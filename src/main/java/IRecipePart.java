
public interface IRecipePart {
	// Grabs the appropriate String or ItemStack for the recipe
	// may depend on position in recipe (0-9)
	Object get(int position);
}
