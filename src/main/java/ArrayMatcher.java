import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		try {
			return piece == stack || OreDictionary.getOres((String) piece).contains(stack);
		} catch (ClassCastException c) {
			Logger.getAnonymousLogger().log(Level.SEVERE, c.getLocalizedMessage() + ": " + part.getClass().getSimpleName() +
				" returned an object that was neither String nor ItemStack");
			return false;
		}
	}

	@Override
	public int size() {
		return parts.length;
	}
}
