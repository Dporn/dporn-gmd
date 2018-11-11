package steem.connect;

import com.github.nmorel.gwtjackson.client.ObjectMapper;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import steem.model.DiscussionComment;

@JsType(namespace = JsPackage.GLOBAL, name = "sc2", isNative = true)
public class SteemConnectV2 {
	
	@JsMethod(name = "Initialize")
	public static native SteemConnectV2Api initialize(SteemConnectInit initializeParam);
	
//	@JsMethod(name = "Initialize")
//	private static native SteemConnectV2Api _initialize(JavaScriptObject param);
	public static interface DiscussionCommentMapper extends ObjectMapper<DiscussionComment>{}
//	@JsOverlay
//	public static SteemConnectV2Api initialize(String app, String callbackUrl, String accessToken, Collection<String> scopes) {
//		GWT.log("SteemConnectV2#initialize");
//		JSONObject param = new JSONObject();
//		param.put("app", new JSONString(app));
//		param.put("callbackURL", new JSONString(callbackUrl));
//		param.put("accessToken", new JSONString(accessToken));
//		JSONArray jscopes = new JSONArray();
//		int ix=0;
//		for (String scope: scopes) {
//			jscopes.set(ix++, new JSONString(scope));
//		}
//		param.put("scope", jscopes);
//		return _initialize(param.getJavaScriptObject());
//	}
//	@JsOverlay
//	public static SteemConnectV2Api initialize(String app, String callbackUrl, String accessToken, String... scopes) {
//		return initialize(app, callbackUrl, accessToken, Arrays.asList(scopes));
//	}

}