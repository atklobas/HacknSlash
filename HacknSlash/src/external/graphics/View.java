package external.graphics;

import java.util.List;

public interface View {

	void render(List<Renderable> rendered, int x, int y);

}
