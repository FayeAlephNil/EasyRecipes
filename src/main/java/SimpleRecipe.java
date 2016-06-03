import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleRecipe extends SlotRecipe {
	private static char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
	protected static IRecipePart[] nullarr = new IRecipePart[9];
	private static List<IRecipePart> nulllist = Arrays.asList(nullarr);

	protected final ArrayMatcher arrMatcher;

	public SimpleRecipe(ItemStack result, Object... parts) {
		super(result, new ArrayMatcher(convert(parts)));
		this.arrMatcher = (ArrayMatcher) this.matcher;
	}
	
	public SimpleRecipe(ItemStack result, Object part) {
		super(result, new ArrayMatcher(allArr(convert(part), 9)));
		this.arrMatcher = (ArrayMatcher) this.matcher;
	}

	public static void add(ItemStack result, Object... parts) {
		GameRegistry.addRecipe(toRecipe(result, parts));
	}

	public static ShapedOreRecipe toRecipe(ItemStack result, Object... parts) {
		return (new SimpleRecipe(result, parts)).toRecipe();
	}

	public void add() {
		GameRegistry.addRecipe(toRecipe());
	}

	public ShapedOreRecipe toRecipe() {
		return toRecipe(result, arrMatcher.partsList);
	}

	protected static ShapedOreRecipe toRecipe(ItemStack result, List<IRecipePart> parts) {
		// Create a mapping from parts to strings to use, map null to a spaaaaaace in order to represent blank spaces in recipes
		HashMap<IRecipePart, String> map = new HashMap<>();
		map.put(null, " ");
		List<Object> topass = new ArrayList<>();

		int i = 0;
		for (IRecipePart part : parts) {
			if (!topass.contains(part) && part != null) {
				topass.add(chars[i]);
				topass.add(part.get(i));
				map.put(part, String.valueOf(chars[i]));
				i++;
			}
		}

		// Pad the list in case someone passed in something with less than 9 elements
		parts.addAll(nulllist);

		// Construct the rows for the recipe from the parts and the lists
		String topRow = row(parts.subList(0, 3), map);
		String midRow = row(parts.subList(3, 6), map);
		String bottomRow = row(parts.subList(6, 9), map);

		List<Object> args = new ArrayList<>();
		args.add(topRow);
		args.add(midRow);
		args.add(bottomRow);
		for (Object e: topass)
			if (e != null)
				args.add(e);

		// Add the recipe using the OreRecipe option
		return new ShapedOreRecipe(result, args.toArray());
	}

	protected static String row(List<IRecipePart> parts, HashMap<IRecipePart, String> map) {
		String result = "";
		for (IRecipePart part : parts) {
			result += map.get(part);
		}
		return result;
	}

	protected static IRecipePart[] convert(Object... parts) {
		IRecipePart[] topass = new IRecipePart[parts.length];
		for (int i = 0; i < parts.length; i++) {
			Object part = parts[i];
			topass[i] = convert(part);
		}
		return topass;
	}

	protected static <T> List<T> allList(T thing, int size) {
		List<T> list = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			list.add(thing);
		}

		return list;
	}

	protected static <T> T[] allArr(T thing, int size) {
		return (T[]) allList(thing, size).toArray();
	}

	protected static IRecipePart convert(Object part) {
		if (part instanceof IRecipePart) {
			return (IRecipePart) part;
		} else if (part instanceof ItemStack) {
			return new Wrapper(part);
		} else if (part instanceof Item) {
			return new Wrapper(new ItemStack((Item) part));
		} else if (part instanceof String) {
			return new Wrapper(part);
		} else if (part instanceof Block) {
			return new Wrapper(new ItemStack((Block) part));
		} else {
			throw new RuntimeException("Received a " + part.getClass().getSimpleName() + " instead of a recipe part");
		}
	}
	
	private static class Wrapper implements IRecipePart {
		private final Object o;

		public Wrapper(Object o) {
			this.o = o;
		}

		@Override
		public Object get(int position) {
			return o;
		}
	}
}
