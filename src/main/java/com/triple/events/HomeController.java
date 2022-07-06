package com.triple.events;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.triple.events.photo.Photo;
import com.triple.events.place.PlaceService;
import com.triple.events.review.Review;
import com.triple.events.review.ReviewService;
import com.triple.events.user.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	UserService userService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	PlaceService placeService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	@RequestMapping(value="/events",method=RequestMethod.POST)
	public ModelAndView reviewAdd(HttpSession session, HttpServletRequest request, Model model) throws ParseException {
		//jsonView 형식
		ModelAndView mav = new ModelAndView("jsonView");
		
		//json 받기 start
		String bodyJson = "";
		StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;
        //한줄씩 담을 변수
        String line = "";
        try {
        	//body내용 inputstream에 담는다.
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                br = new BufferedReader(new InputStreamReader(inputStream));
                //더 읽을 라인이 없을때까지 계속
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }else {
            }
        } catch (IOException e) {
        	e.printStackTrace();
        } 
        bodyJson = stringBuilder.toString();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
	        //parsing
			jsonObject = (JSONObject) jsonParser.parse(bodyJson);
			String type = (String) jsonObject.get("type");
			String review = "REVIEW";
			if(type.toUpperCase().equals(review)) {
				String action = (String) jsonObject.get("action");
				String reviewID = (String) jsonObject.get("reviewID");
				String content = (String) jsonObject.get("content");
				JSONArray attactedPhotoIds= (JSONArray) jsonObject.get("attactedPhotoIds");
				String userID = (String) jsonObject.get("userID");
				int  userCheck = userService.idCheck(userID);
				if(userCheck ==0) {
					mav.addObject("result","false");
					mav.addObject("msg","없는 계정입니다.");
					return mav;
				}
				String placeID = (String) jsonObject.get("placeID");
				int placeCheck = placeService.placeCheck(placeID);
				if(placeCheck==0) {
					mav.addObject("result","false");
					mav.addObject("msg","없는 장소입니다.");
					return mav;
				}
				Review reviewDTO = new Review();
				//ADD, MOD, DELETE
				reviewDTO.setReviewID(reviewID);
				reviewDTO.setPlaceID(placeID);
				reviewDTO.setUserID(userID);
				reviewDTO.setAction(action);
				if(action.toUpperCase().equals("ADD")) {
					String reviewcheck = reviewService.reviewCheck(reviewDTO);
					if(reviewcheck.equals("0")) {
						int addPoint=0;
						reviewDTO.setContent(content);
						if(attactedPhotoIds !=null) {
							int photoSize = attactedPhotoIds.size();
							Photo[] photo = new Photo[photoSize];
							if(photoSize >0) {
								for(int i=0; i<photoSize;i++) {
									photo[i]=new Photo();
									photo[i].setPhotoID((String)attactedPhotoIds.get(i));
									photo[i].setReviewID(reviewID);
								}
							}
							addPoint = reviewService.insert(reviewDTO,photo);
						}else {
							Photo[] photo = new Photo[0];
							addPoint = reviewService.insert(reviewDTO,photo);
						}
						mav.addObject("result","true");
						mav.addObject("reviewID",reviewID);
						mav.addObject("addPoint",addPoint);
					}else {
						mav.addObject("result","false");
						mav.addObject("msg","이미 리뷰가 등록 되어 있습니다.MOD or DELETE를 사용하세요");
					}
				}else if(action.toUpperCase().equals("DELETE")) {
					String reviewcheck = reviewService.reviewCheck(reviewDTO);
					if(reviewcheck.equals("0")) {
						mav.addObject("result","false");
						mav.addObject("msg","삭제 할 리뷰가 없습니다. ADD를 사용하세요.");
					}else {
						int deletePoint=reviewService.delete(reviewDTO);
						mav.addObject("result","true");
						mav.addObject("deletePoint",deletePoint);
					}
				}else if(action.toUpperCase().equals("MOD")){
					String reviewcheck = reviewService.reviewCheck(reviewDTO);
					if(reviewcheck.equals("0")) {
						mav.addObject("result","false");
						mav.addObject("msg","수정 할 리뷰가 없습니다. ADD를 사용하세요.");
					}else {
						//기존 포인트 , 수정 될 포인트 증감량
						int[] arr= new int[2];
						reviewDTO.setContent(content);
						if(attactedPhotoIds !=null) {
							int photoSize = attactedPhotoIds.size();
							Photo[] photo = new Photo[photoSize];
							if(photoSize >0) {
								for(int i=0; i<photoSize;i++) {
									photo[i]=new Photo();
									photo[i].setPhotoID((String)attactedPhotoIds.get(i));
									photo[i].setReviewID(reviewID);
								}
							}
							arr = reviewService.modify(reviewDTO,photo);
						}else {
							Photo[] photo = new Photo[0];
							arr = reviewService.modify(reviewDTO,photo);
						}
						if(arr!=null){
							mav.addObject("result","true");
							mav.addObject("reviewID",reviewID);
							mav.addObject("beforePoint",arr[0]);
							mav.addObject("addPoint",arr[1]);
							mav.addObject("point",arr[0]+arr[1]);
						}else{
							mav.addObject("result","false");
							mav.addObject("msg","수정 할 리뷰가 없습니다. ADD를 사용하세요.");
						}
						
					}
				}else {
					mav.addObject("result","false");
					mav.addObject("msg","add, mod, delete중 하나를 사용하세요");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value="/inquire",method=RequestMethod.GET)
	public ModelAndView reviewManage(HttpSession session, HttpServletRequest request) throws ParseException {
		//jsonView 형식
		ModelAndView mav = new ModelAndView("jsonView");
		String userID = request.getParameter("userID");
		int  userCheck = userService.idCheck(userID);
		String placeID = request.getParameter("placeID");
		int placeCheck = placeService.placeCheck(placeID);
		if(userCheck ==0) {
			mav.addObject("result","false");
			mav.addObject("msg","없는 계정입니다.");
		}
		
		if(userID!=null&&placeID!=null) {
			if(placeCheck==0) {
				mav.addObject("result","false");
				mav.addObject("msg","없는 장소입니다. ");
			}
			List<Review> list = reviewService.manageUserPlace(userID,placeID);
			JSONArray history= new JSONArray();
			int point =0;
			for(int i =0;i<list.size();i++) {
				String date = list.get(i).getDate();
				String action = list.get(i).getAction();
				int tmpPoint = list.get(i).getPoint();
				history.add("date:"+date+", action:"+action+", point:"+tmpPoint);
				if(i==list.size()-1) {
					point = tmpPoint;
				}
			}
			mav.addObject("userID",userID);
			mav.addObject("placeID",placeID);
			mav.addObject("history", history);
			mav.addObject("point", point);
		}else if(userID!=null&&placeID==null) {
			int totalCount = userService.getTotalCount(userID);
			mav.addObject("totalCount", totalCount);
			mav.addObject("userID", userID);
			List<String> placeIDs = reviewService.getPlaceIDs(userID);
			JSONArray jsonArray= new JSONArray();
			for(int i=0;i<placeIDs.size();i++) {
				List<Review> list = reviewService.manageUserPlace(userID,placeIDs.get(i));
				JSONArray history= new JSONArray();
				JSONObject jsonOb = new JSONObject();
				int point = 0;
				for(int j =0;j<list.size();j++) {
					String date = list.get(j).getDate();
					String action = list.get(j).getAction();
					int tmpPoint = list.get(j).getPoint();
					history.add("date:"+date+", action:"+action+", point:"+tmpPoint);
					if(j==list.size()-1) {
						point = tmpPoint;
					}
				}
				jsonOb.put("point", point);
				jsonOb.put("placeID", placeIDs.get(i));
				jsonOb.put("history", history);
				jsonArray.add(jsonOb);
			}
			mav.addObject("items",jsonArray);
		}
		return mav;
	}
}
