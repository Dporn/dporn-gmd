package co.dporn.gmd.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import co.dporn.gmd.shared.Post;

@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
@Path("/")
public class DpornCoEmbed {
	private static String _template;
	private static String template() {
		if (_template==null) {
			try {
				_template=IOUtils.toString(DpornCoEmbed.class.getResourceAsStream("/embed/embed-player.html"));
			} catch (IOException e) {
			}
		}
		return _template;
	}
	
	@Context
	private HttpServletResponse response;
	
	private static Map<String, String> cache = Collections.synchronizedMap(new HashMap<>());
	@Produces(MediaType.TEXT_HTML)
	@Path("@{authorname}/{permlink}")
	@GET
	public String player(@PathParam("authorname") String author, @PathParam("permlink") String permlink, @QueryParam("base-url") String baseUrl) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.TEXT_HTML);
		String key = author.trim()+"|"+permlink.trim();
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		Post post = MongoDpornoCo.getPost(author, permlink);
		isValid: {
			if (post==null) {
				break isValid;
			}
			if (!author.equals(post.getAuthor())) {
				break isValid;
			}
			String coverImageIpfs = post.getCoverImageIpfs();
			if (coverImageIpfs==null) {
				break isValid;
			}
			coverImageIpfs=coverImageIpfs.trim();
			if (coverImageIpfs.length()!=46) {
				break isValid;
			}
			String videoIpfs = post.getVideoIpfs();
			if (videoIpfs==null) {
				break isValid;
			}
			videoIpfs=videoIpfs.trim();
			if (videoIpfs.length()!=46) {
				break isValid;
			}
			String embedHtml = template();
			embedHtml=embedHtml.replace("__TITLE__", StringEscapeUtils.escapeXml10(post.getTitle()));
			embedHtml=embedHtml.replace("__POSTERHASH__", StringEscapeUtils.escapeXml10(coverImageIpfs));
			embedHtml=embedHtml.replace("__VIDEOHASH__", StringEscapeUtils.escapeXml10(videoIpfs));
			if (cache.size()>100) {
				cache.clear();
			}
			cache.put(key, embedHtml);
			return embedHtml;
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "NOT FOUND";
		
	}
	
}
