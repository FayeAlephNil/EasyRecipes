import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ArrayMatcher implements IMatcher {
	public final IRecipePart[] parts;
	public final List<IRecipePart> partsList;

	public ArrayMatcher(IRecipePart... parts) {
		this.parts = parts;
		this.partsList = Arrays.asList(parts);
	}

	@Override
	public boolean matches(ItemStack stack, int slot) {
		return parts[slot].get(slot) == stack;
	}

	@Override
	public int size() {
		return parts.length;
	}
}
