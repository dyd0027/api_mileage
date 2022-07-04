package com.triple.events.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.triple.events.photo.Photo;
import com.triple.events.review.Review;
import com.triple.events.review.ReviewService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	ReviewService reviewService;
	
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
	        //json 형태로 변환하기
			jsonObject = (JSONObject) jsonParser.parse(bodyJson);
			String type = (String) jsonObject.get("type");
			String review = "REVIEW";
			if(type.toUpperCase().equals(review)) {
				String action = (String) jsonObject.get("action");
				String reviewID = (String) jsonObject.get("reviewID");
				String content = (String) jsonObject.get("content");
				JSONArray attactedPhotoIds= (JSONArray) jsonObject.get("attactedPhotoIds");
				String userID = (String) jsonObject.get("userID");
				String placeID = (String) jsonObject.get("placeID");
				Review reviewDTO = new Review();
				if(action.toUpperCase().equals("ADD")) {
					reviewDTO.setPlaceID(placeID);
					reviewDTO.setUserID(userID);
					String reviewcheck = reviewService.reviewCheck(reviewDTO);
					if(reviewcheck.equals("0")) {
						int addPoint=0;
						reviewDTO.setReviewID(reviewID);
						reviewDTO.setAction(action);
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
					reviewDTO.setReviewID(reviewID);
					reviewDTO.setPlaceID(placeID);
					reviewDTO.setUserID(userID);
					reviewDTO.setAction(action);
					String reviewcheck = reviewService.reviewCheck(reviewDTO);
					if(reviewcheck.equals("0")) {
						mav.addObject("result","false");
						mav.addObject("msg","삭제 할 리뷰가 없습니다. ADD를 사용하세요.");
					}else {
						int deletePoint=reviewService.delete(reviewDTO);
						mav.addObject("result","true");
						mav.addObject("deletePoint",deletePoint);
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return mav;
	}
}
