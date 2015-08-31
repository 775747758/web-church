package com.unitever.module.goods.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.unitever.module.user.model.User;
import com.unitever.platform.component.attachment.annotation.AttachmentAnnotation;

@AttachmentAnnotation
public class Goods implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String kind;
	private String originalPrice;
	private String price;
	private String size;
	private String note;
	private User user;
	private String ft;
	private String lt;
	private String fu;
	private String lu;
	
	private String kindValue;
	private String noteAbstract;
	private List<String> picUrls;
	
	private String totalCount;
	
	/**首次消费商品*/
	public static final String GOODS_KIND_FIRST = "1";
	/**返单消费商品*/
	public static final String GOODS_KIND_SECOND = "2";
	/**固定消费商品*/
	public static final String GOODS_KIND_FIXED = "3";
	
	public Goods() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Goods(String id) {
		this.id = id;
	}
	
	public Goods(String id, String name, String kind, String price,
			String size, String note) {
		super();
		this.id = id;
		this.name = name;
		this.kind = kind;
		this.price = price;
		this.size = size;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getKindValue() {
		if(GOODS_KIND_FIRST.equals(kind)) {
			return "首单消费商品";
		} else if(GOODS_KIND_SECOND.equals(kind)) {
			return "返单消费商品";
		}
		return kindValue;
	}

	public void setKindValue(String kindValue) {
		this.kindValue = kindValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFt() {
		return ft;
	}

	public void setFt(String ft) {
		this.ft = ft;
	}

	public String getLt() {
		return lt;
	}

	public void setLt(String lt) {
		this.lt = lt;
	}

	public String getFu() {
		return fu;
	}

	public void setFu(String fu) {
		this.fu = fu;
	}

	public String getLu() {
		return lu;
	}

	public void setLu(String lu) {
		this.lu = lu;
	}
	public String getNoteAbstract() {
		if(StringUtils.isNotBlank(note) && note.length()>20) {
			return note.substring(0, 20)+"...";
		}
		return note;
	}

	public void setNoteAbstract(String noteAbstract) {
		this.noteAbstract = noteAbstract;
	}

	public List<String> getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(List<String> picUrls) {
		this.picUrls = picUrls;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
