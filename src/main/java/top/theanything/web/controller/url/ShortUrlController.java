package top.theanything.web.controller.url;

import top.theanything.anno.Controller;
import top.theanything.anno.RequestMapping;
import top.theanything.config.BasicConfig;
import top.theanything.core.action.AbstractAction;
import top.theanything.core.http.Request;
import top.theanything.core.http.Response;
import top.theanything.web.error.ShortPathNotFoundException;
import top.theanything.web.service.ShortUrlService;

import java.io.IOException;

/**
 * @author zhou
 * @Description
 * @createTime 2020-05-21
 */
@Controller
public class ShortUrlController extends AbstractAction {
	private static ShortUrlService service = null;
	static {
		service = new ShortUrlService();
	}

	@RequestMapping(value = "/shortUrl")
	public void shortUrl(Request request , Response response) throws IOException {
		response.sendFile(response.PREFIX_PATH + BasicConfig.shortUrlPath);
		return;
	}

	/**
	 * 生成短链接
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getUrl") // Http请求默认设置为GET
	public String generatorUrl(Request req , Response response){
		String url = (String) req.getParams().get("shortUrl");

		String shortUrl = service.genShortUrl(url);

		return shortUrl;
	}

	/**
	 * 从短链接跳转到长链接网页
	 * @param request
	 * @param response
	 * @return
	 * @throws ShortPathNotFoundException  没有该短链接
	 */
	@RequestMapping(value = "/s") // Http请求默认设置为GET
	public String redirectReal(Request request , Response response) throws ShortPathNotFoundException{
		String  KeyID = (String) request.getParams().get("p");

		String realPath ;
		if( (realPath = service.getRealPath(KeyID) ) == null){
			throw new ShortPathNotFoundException();
		}
		return "redirect:"+realPath;
	}
}
