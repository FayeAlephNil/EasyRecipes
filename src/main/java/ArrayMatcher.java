import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements @IMatcher
 */
public class ArrayMatcher implements IMatcher {
	public final IRecipePart[] parts;
	public final List<IRecipePart> partsList;

	public ArrayMatcher(IRecipePart... parts) {
		this.parts = parts;
		this.partsList = Arrays.asList(parts);
	}

	@Override
	public boolean matches(ItemStack stack, int slot) {
		IRecipePart part = parts[slot];
		Object piece = part.get(slot);
		if (piece instanceof ItemStack) {
			return ItemStack.areItemStacksEqual((ItemStack) piece, stack);
		} else if (piece instanceof String) {
			return OreDictionary.getOres((String) piece).contains(stack);
		} else {
			String toLog = part.getClass().getSimpleName() + " returned an object that was neither String nor ItemStack, but was"
				+ piece.getClass().getSimpleName();
			Logger.getAnonymousLogger().log(Level.SEVERE, toLog);
			return false;
		}
	}

	@Override
	public int size() {
		return parts.length;
	}
}
