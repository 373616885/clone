package com.whale.feign.service;

import com.whale.feign.bean.BussinessReq;
import com.whale.feign.bean.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author qinjp
 * @date 2019-06-18
 **/
@FeignClient(value = "data-xinyan", qualifier = "xinyanService")
public interface XinyanService {

	/**
	 * FeignClient中的方法有@PostMapping注解
	 * 则微服务TaskApiController中对应方法的注解也应当保持一致为@PostMapping
	 * 若果不一致，则会报404的错误
	 */
	@PostMapping(value = "/xinyan/overdue")
	//@RequestMapping(value = "/xinyan/overdue", method = RequestMethod.POST)
	Result overdue(BussinessReq req);
	/**
	 * 如果在FeignClient中的方法有参数传递一般要加@RequestParam（“xxx”）注解
	 * 错误写法:
	 @PostMapping(value = "taskApiController/getAll")
	 List<TaskVO> getAll(String name);
	 或
	 @PostMapping(value = "taskApiController/getAll")
	 List<TaskVO> getAll(@RequestParam String name);
	 正确写法：
	 @PostMapping(value = "taskApiController/getAll")
	 List<TaskVO> getAll(@RequestParam("name") String name);

	 FeignClient中post传递对象和consumes = "application/json"

	 *
	 */
	@RequestMapping(value = "/xinyan/probe", method = RequestMethod.POST)
	Result probe(BussinessReq req);

	@RequestMapping(value = "/xinyan/radar", method = RequestMethod.POST)
	Result radar(BussinessReq req);

	@RequestMapping(value = "/xinyan/totaldebt", method = RequestMethod.POST)
	Result totaldebt(BussinessReq req);


}

