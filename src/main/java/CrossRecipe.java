import net.minecraft.item.ItemStack;

public class CrossRecipe extends SimpleRecipe {
	public CrossRecipe(ItemStack result, Object crosser, Object bounder) {
		super(result, new CrossPart(convert(crosser), convert(bounder)));
	}

	private static class CrossPart implements IRecipePart {

		private final IRecipePart crosser;
		private final IRecipePart bounder;

		public CrossPart(IRecipePart crosser, IRecipePart bounder) {
			this.crosser = crosser;
			this.bounder = bounder;
		}

		@Override
		public Object get(int position) {
			return position % 2 == 0 ? bounder.get(position) : crosser.get(position);
		}
	}
}
