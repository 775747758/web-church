package com.unitever.platform.component.baseform.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unitever.platform.component.abstractUC.bean.Component;
import com.unitever.platform.component.baseform.bean.RadioListBean;

public class RadioListTag extends AbstractListTag {

	private static final long serialVersionUID = -1231035864473929464L;

	@Override
	public Component getBean(HttpServletRequest request, HttpServletResponse response) {
		return new RadioListBean(request, response);
	}

}
