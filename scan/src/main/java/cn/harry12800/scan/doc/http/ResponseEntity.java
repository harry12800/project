package cn.harry12800.scan.doc.http;

import java.util.Objects;

import cn.harry12800.api.doc.http.EResponseCode;

public class ResponseEntity {

	private int code = 0;

	private String message = "";

	private Object content = null;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = Objects.toString(message, "").trim();
	}

	public Object getContent() {
		return content;
	}

	public boolean checkSuccess() {
		if (code == EResponseCode.OK.value()
				|| code == EResponseCode.CREATED.value()
				|| code == EResponseCode.ACCEPTED.value()) {
			return true;
		}
		return false;
	}

	public ResponseEntity setContent(Object content) {
		this.content = content;

		return this;
	}

	public static ResponseEntity newOk() {
		ResponseEntity r = new ResponseEntity();
		r.setCode(EResponseCode.OK.value());
		r.setMessage(EResponseCode.OK.toDisplayText());

		return r;
	}

	public static ResponseEntity newAccepted() {
		ResponseEntity r = new ResponseEntity();
		r.setCode(EResponseCode.ACCEPTED.value());
		r.setMessage(EResponseCode.ACCEPTED.toDisplayText());

		return r;
	}

	public static ResponseEntity newCreated() {
		ResponseEntity r = new ResponseEntity();
		r.setCode(EResponseCode.CREATED.value());
		r.setMessage(EResponseCode.CREATED.toDisplayText());

		return r;
	}

	public static ResponseEntity newBadRequest() {
		ResponseEntity r = new ResponseEntity();
		r.setCode(EResponseCode.BAD_REQUEST.value());
		r.setMessage(EResponseCode.BAD_REQUEST.toDisplayText());

		return r;
	}

	public static ResponseEntity newNoContent() {
		ResponseEntity r = new ResponseEntity();
		r.setCode(EResponseCode.NO_CONTENT.value());
		r.setMessage(EResponseCode.NO_CONTENT.toDisplayText());

		return r;
	}

	public static ResponseEntity newNotAcceptAble() {
		ResponseEntity r = new ResponseEntity();
		r.setContent(EResponseCode.NOT_ACCEPTABLE.value());
		r.setMessage(EResponseCode.NOT_ACCEPTABLE.toDisplayText());

		return r;
	}

	public static ResponseEntity newUnautorized() {
		String msg = EResponseCode.UNAUTHORIZED.toDisplayText();
		return newUnautorized(msg);
	}

	public static ResponseEntity newUnautorized(String msg) {
		String err = null;
		return newUnautorized(msg, err);
	}

	public static ResponseEntity newUnautorized(String msg, String err) {
		ResponseEntity r = new ResponseEntity();
		r.setCode(EResponseCode.UNAUTHORIZED.value());
		r.setMessage(msg);
		r.setContent(err);

		return r;
	}

	public static ResponseEntity newNotFound() {
		ResponseEntity r = new ResponseEntity();
		r.setCode(EResponseCode.NOT_FOUND.value());
		r.setMessage(EResponseCode.NOT_FOUND.toDisplayText());

		return r;
	}

	public static ResponseEntity newServerError() {
		String msg = EResponseCode.SERVER_ERROR.toDisplayText();
		return newServerError(msg);
	}

	public static ResponseEntity newServerError(String msg) {
		String err = null;
		return newServerError(msg, err);
	}

	public static ResponseEntity newServerError(String msg, String err) {
		ResponseEntity r = new ResponseEntity();
		r.setCode(EResponseCode.SERVER_ERROR.value());
		r.setMessage(EResponseCode.SERVER_ERROR.toDisplayText());

		return r;
	}

	public static class PageInfo {
		private int pageIndex = 0;
		private int pageSize = 10;
		private int total = 0;

		public PageInfo() {

		}

		public PageInfo(int pageIndex, int pageSize, int total) {
			this.setPageIndex(pageIndex);
			this.setPageSize(pageSize);
			this.setTotal(total);
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			if (total >= 0) {
				this.total = total;
			}
		}

		public int getPageIndex() {
			return pageIndex;
		}

		public void setPageIndex(int pageIndex) {
			if (pageIndex >= 0) {
				this.pageIndex = pageIndex;
			}
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			if (pageSize >= 0) {
				this.pageSize = pageSize;
			}
		}
	}

}
