import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class SlotRecipe implements IRecipe {

	protected final IMatcher matcher;
	protected final ItemStack result;

	public SlotRecipe(ItemStack result, IMatcher matcher) {
		this.result = result;
		this.matcher = matcher;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		boolean result = true;
		for (int n : range(0, inv.getSizeInventory())) {
			result = result && matcher.matches(inv.getStackInSlot(n), n);
		}
		return result;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return result.copy();
	}

	@Override
	public int getRecipeSize() {
		return matcher.size();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return result;
	}

	private int[] range(int n, int m) {
		int[] result = new int[m - n + 1];
		for (int i = n; i <= m; i++) {
			result[i - n] = i;
		}
		return result;
	}
}
