import React, { useEffect, useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { generatePDF } from "../api/pdf";

import "../styles/student_test_result.css";

const EDIT_RESULT_URL = "/api/test/result/update";
const RETEST_URL = "/api/test/custom";

const StudentTestResult = () => {
  const { test_id, user_id } = useParams();
  const [username, setUsername] = useState("");
  const [testName, setTestName] = useState("");
  const [imageLink, setIamageLink] = useState("");
  const [scores, setScores] = useState({});
  const [resultData, setResultData] = useState([]);
  const navigate = useNavigate();

  const STUDENT_RESULT_URL = `/api/test/result/${test_id}/${user_id}`;
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    try {
      axiosPrivate.get(STUDENT_RESULT_URL).then((response) => {
        setUsername(response.data.name);
        setTestName(response.data.testName);
        setScores({
          totalNumber: response.data.totalNumber,
          passScore: response.data.passScore,
          totalScore: response.data.totalScore,
        });
        setResultData(response.data.contentList);
        setIamageLink(response.data.url);
      });
    } catch (error) {
      console.log("ERROR FETCHING TEST DATA", error);
    }
  }, []);
  const { class_id } = useParams();

  const handleResultChange = (e, contentId) => {
    console.log(contentId);
    axiosPrivate
      .put(
        EDIT_RESULT_URL,
        JSON.stringify({
          contentId: contentId,
        })
      )
      .then(navigate(0));
  };

  const handleRetest = (e) => {
    try {
      axiosPrivate
        .post(RETEST_URL, JSON.stringify(user_id))
        .then((response) => {
          generatePDF(response.data.name, response.data.testContentList, [
            { username: username },
          ]);
        });
    } catch (error) {
      console.log("ERROR FETCHING CUSTOM TEST DATA", error);
    }
  };

  return (
    <div id="student_result_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="test" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="page_title">{username} 의 시험결과</div>
          <div id="test_info_container">
            <div>
              <p>시험이름 : {testName}</p>
              <p>
                총 문제 수 : {scores.totalNumber} | 통과 점수 :
                {scores.passScore} | 내 점수 : {scores.totalScore}
              </p>
            </div>
            <div id="test_info_right_container">
              <a id="image_link" href={imageLink}>
                시험지 확인
              </a>
              <button id="retest_button" onClick={(e) => handleRetest(e)}>
                오답 맞춤 출제
              </button>
            </div>
          </div>

          <div>
            <div className="result_label">
              <p className="result_content">번호</p>
              <p className="result_content">문제</p>
              <p className="result_content">정답</p>
              <p className="result_content">답안</p>
              <p className="result_content">채점</p>
            </div>
            {resultData.map((result, i) => (
              <div key={i} className="question_result">
                <p className="result_content">{i + 1}</p>
                <p className="result_content">{result.question}</p>
                <p className="result_content">{result.answer}</p>
                <p className="result_content">{result.userAnswer}</p>
                <p className="result_content">{result.result ? "O" : "X"}</p>
                <button
                  className="edit_result_button"
                  onClick={(e, i) => {
                    handleResultChange(e, result.contentId);
                  }}
                >
                  {result.result ? "X" : "O"}로 변경
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentTestResult;
