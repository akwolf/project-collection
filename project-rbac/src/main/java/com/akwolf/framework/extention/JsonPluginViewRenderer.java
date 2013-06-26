package com.akwolf.framework.extention;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.et.mvc.ViewContext;
import com.et.mvc.renderer.AbstractViewRenderer;

public class JsonPluginViewRenderer extends
		AbstractViewRenderer<JsonPluginView> {

	@Override
	protected void renderView(JsonPluginView view, ViewContext viewContext)
			throws Exception {
		if (view == null) {
			return;
		}

		HttpServletResponse response = viewContext.getResponse();
		if (view.getContentType() != null) {
			response.setContentType(view.getContentType());
		} else {
			response.setContentType("application/json;charset=utf-8");
		}
		PrintWriter out = response.getWriter();
		out.print(view.toString());
		out.close();
	}
}
