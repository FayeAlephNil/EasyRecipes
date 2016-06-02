import net.minecraft.item.ItemStack;

public interface IMatcher {
	boolean matches(ItemStack stack, int slot);

	int size();
}
