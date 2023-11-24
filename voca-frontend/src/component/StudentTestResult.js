import React from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import { useParams } from "react-router-dom";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

import "../styles/student_test_result.css";

const EDIT_RESULT_URL = "";

const StudentTestResult = () => {
  const axiosPrivate = useAxiosPrivate();
  const { class_id } = useParams();
  const username = "문서형";
  const testname = "정기시험 3";
  const resultData = [
    {
      number: 1,
      question: "dark",
      correct: "어두운",
      answer: "깊은",
      right: false,
    },
    {
      number: 2,
      question: "donation",
      correct: "기부",
      answer: "고슴도치",
      right: false,
    },
    {
      number: 3,
      question: "pressure",
      correct: "압력",
      answer: "압력",
      right: true,
    },
  ];

  const handleResultChange = (e, i) => {
    resultData[i].right = !resultData[i].right;
    axiosPrivate.post(EDIT_RESULT_URL, resultData);
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
          <div id="page_title">{username}의 시험결과</div>
          <div id="test_info_container">
            <div>
              <p>시험이름 : {testname}</p>
              <p>총 문제 수 : 10 | 통과 점수 : 5 | 내 점수 : 10</p>
            </div>
            <div>
              <a> 시험지 다운로드 </a>
            </div>
          </div>

          <div>
            {resultData.map((result, i) => (
              <div key={i} className="question_result">
                <p className="result_content">{result.number}</p>
                <p className="result_content">{result.question}</p>
                <p className="result_content">{result.correct}</p>
                <p className="result_content">{result.answer}</p>
                <p className="result_content">{result.right ? "O" : "X"}</p>
                <button
                  className="edit_result_button"
                  onClick={(e, i) => {
                    handleResultChange(e, i);
                  }}
                >
                  {result.right ? "X" : "O"}로 변경
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
