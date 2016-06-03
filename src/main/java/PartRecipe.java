import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PartRecipe extends SimpleRecipe {
	public PartRecipe(ItemStack result, Object part) {
		super(result, allArr(part, 9));
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
}
