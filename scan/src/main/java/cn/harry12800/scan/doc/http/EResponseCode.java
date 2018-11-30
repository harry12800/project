package cn.harry12800.scan.doc.http;

import org.springframework.http.HttpStatus;

public enum EResponseCode {
	OK(10, HttpStatus.OK.name()), // HttpCode(200)
	CREATED(11, HttpStatus.CREATED.name()), // HttpCode(201)
	ACCEPTED(12, HttpStatus.ACCEPTED.name()), // HttpCode(202)
	NO_CONTENT(14, HttpStatus.NO_CONTENT.name()), // HttpCode(104)
	BAD_REQUEST(20, HttpStatus.BAD_REQUEST.name()), // HttpCode(400)
	UNAUTHORIZED(21, HttpStatus.UNAUTHORIZED.name()), // HttpCode(401)
	FORBIDDEN(23, HttpStatus.FORBIDDEN.name()), // HttpCode(403)
	NOT_FOUND(24, HttpStatus.NOT_FOUND.name()), // HttpCode(404)
	NOT_ALLOWED(25, HttpStatus.METHOD_NOT_ALLOWED.name()), // HttpCode(405)
	NOT_ACCEPTABLE(26, HttpStatus.NOT_ACCEPTABLE.name()), // HttpCode(406)
	SERVER_ERROR(30, HttpStatus.INTERNAL_SERVER_ERROR.name()); // HttpCode(500)

	private String desc = "";
	private int value = 0;

	private EResponseCode(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int value() {
		return this.value;
	}

	public String desc() {
		return this.desc;
	}

	public String toDisplayText() {
		String msg = "";
		switch (this) {
		case OK:
			msg = "Ok";
			break;
		case CREATED:
			msg = "创建成功";
			break;
		case ACCEPTED:
			msg = "操作成功";
			break;
		case NO_CONTENT:
			msg = "内容为空";
			break;
		case BAD_REQUEST:
			msg = "错误请求";
			break;
		case UNAUTHORIZED:
			msg = "权限受限";
			break;
		case FORBIDDEN:
			msg = "操作禁止";
			break;
		case NOT_FOUND:
			msg = "资源未找到";
			break;
		case NOT_ALLOWED:
			msg = "操作未允许";
			break;
		case NOT_ACCEPTABLE:
			msg = "操作未接受";
			break;
		case SERVER_ERROR:
			msg = "服务器错误";
			break;
		default:
			break;
		}

		return msg;
	}

}
