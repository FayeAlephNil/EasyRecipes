import net.minecraft.item.ItemStack;

public class CenteredRecipe extends PartRecipe {
	public CenteredRecipe(ItemStack result, Object center, Object outer, Object inner) {
		this(result, convert(center), convert(outer), convert(inner));
	}

	public CenteredRecipe(ItemStack result, IRecipePart center, IRecipePart outer, IRecipePart inner) {
		super(result, new CenteredPart(center, outer, inner));
	}

	public static CenteredRecipe cross(ItemStack result, Object crosser, Object bounder) {
		return new CenteredRecipe(result, crosser, crosser, bounder);
	}

	public static CenteredRecipe plus(ItemStack result, Object plus, Object bounder) {
		return new CenteredRecipe(result, plus, bounder, plus);
	}

	private static class CenteredPart implements IRecipePart {

		private final IRecipePart center;
		private final IRecipePart outer;
		private final IRecipePart inner;

		public CenteredPart(IRecipePart center, IRecipePart outer, IRecipePart inner) {
			this.center = center;
			this.outer = outer;
			this.inner = inner;
		}

		@Override
		public Object get(int position) {
			if (position == 4) {
				return center;
			} else if (position % 2 == 0) {
				return outer;
			} else {
				return inner;
			}
		}
	}
}
