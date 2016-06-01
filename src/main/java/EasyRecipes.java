import com.sun.corba.se.impl.io.TypeMismatchException;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

public class EasyRecipes {

	private static char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};

	private static IRecipePart[] nullarr = new IRecipePart[9];
	private static List<IRecipePart> nulllist = Arrays.asList(nullarr);

	public static void add(ItemStack result, Object... parts) throws TypeMismatchException {
		GameRegistry.addRecipe(toRecipe(result, parts));
	}

	public static ShapedOreRecipe toRecipe(ItemStack result, Object... parts) throws TypeMismatchException {
		List<IRecipePart> topass = new ArrayList<>();
		for (Object part : parts) {
			if (part instanceof IRecipePart) {
				topass.add((IRecipePart) part);
			} else if (part instanceof ItemStack) {
				topass.add(new Wrapper(part));
			} else if (part instanceof Item) {
				topass.add(new Wrapper(new ItemStack((Item) part)));
			} else if (part instanceof String) {
				topass.add(new Wrapper(part));
			} else {
				throw new TypeMismatchException("Received a " + part.getClass().getSimpleName() + " instead of a recipe part");
			}
		}
		return toRecipe(result, topass);
	}

	private static ShapedOreRecipe toRecipe(ItemStack result, List<IRecipePart> parts) {
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

	private static String row(List<IRecipePart> parts, HashMap<IRecipePart, String> map) {
		String result = "";
		for (IRecipePart part : parts) {
			result += map.get(part);
		}
		return result;
	}

	// Intended for ItemStacks and Strings
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
}}