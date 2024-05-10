package com.github.springprac.web.controller.sample;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SessionTokenSampleController {


    @GetMapping("/set-session") // 세션 정의
    public String setSession(HttpSession session){
        session.setAttribute("user","이동욱");
        session.setAttribute("gender","남자");
        session.setAttribute("job","배우");
        return "Session Set succcessfully";
    }

    @GetMapping("/set-session2") // 세션 정의
    public String setSession2(HttpSession session){
        session.setAttribute("user","김채원");
        session.setAttribute("gender","여자");
        session.setAttribute("job","가수");
        return "Session Set succcessfully";
    }


    @GetMapping("/get-session") // 세션 가져오기
    public String getSession(HttpSession session){
        String user = (String) session.getAttribute("user");
        String gender = (String) session.getAttribute("gender");
        String job = (String) session.getAttribute("job");
        return String.format("안녕하세요 , 직업 : %s 성별: %s인 %s 입니다." , job, gender, user);

    }

    @GetMapping("/generate-token")
    public String generateToken(HttpServletResponse httpServletResponse) {
        String jwt = Jwts.builder()
                .setSubject("token1")
                .claim("user", "이동욱")
                .claim("gender", "남자")
                .claim("job", "배우")
                .compact(); // 헤더로 해당 정보 가진 토큰 전송
        httpServletResponse.addHeader("Token",jwt);
        return "JWT set Successfully";
    }

    @GetMapping("/show-token")
    public String showToken(@RequestHeader("Token") String token) { // 전송된 토큰 받아서 @RequestHeader어노테이션을 통해 해당 파라미터에 토큰 정보 받아주고
        Claims claims = Jwts.parser()
                .parseClaimsJwt(token) // Jwts 파서가 parseClaime으로 해석함
                .getBody(); // 해석된 값을 getbody에 넣음

        String user = (String) claims.get("user"); // claims가 get해서 각각의 속성 얻음
        String gender = (String) claims.get("gender");
        String job = (String) claims.get("job");
        return String.format("안녕하세요 , 직업 : %s 성별: %s인 %s 입니다." , job, gender, user); // 얻은 값 리턴해서 결과 출력
    }


}
