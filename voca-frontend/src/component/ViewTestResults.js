import React, { useState, getParam, useEffect } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";

import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useNavigate, useParams } from "react-router-dom";

//page that represents each students' test result.
import "../styles/view_test_results.css";

const TEST_RESULT_URL = "/api/test/result/";
const PAPER_SUBMIT_URL = "/api/test/start/";
const TEST_CONTENT_URL = "/api/test/start/";

const ViewTestResult = () => {
  const { class_id, test_id } = useParams();
  const [file, setFile] = useState();
  const [studentResults, setStudentResults] = useState([]);
  const [testName, setTestName] = useState("");
  const { key } = useParams();
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  useEffect(() => {
    try {
      axiosPrivate.get(TEST_CONTENT_URL + test_id).then((response) => {
        setTestName(response.data.name);
      });
      axiosPrivate.get(`/api/test/result/${test_id}`).then((response) => {
        setStudentResults(response.data);
        console.log(response.data);
      });
    } catch (error) {
      console.log("ERROR FETCHING STUDENT RESULTS", error);
    }
  }, []);

  const handleSubmitClick = () => {
    const element = document.getElementById("file_input");
    element.click();
  };

  const handleTestContent = (e) => {
    navigate(`/class/${class_id}/test/content/${test_id}`);
  };

  const handleFileChange = (event) => {
    try {
      const formData = new FormData();
      if (event.target.files[0]) {
        formData.append("file", event.target.files[0]);
        const response = axiosPrivate
          .post(PAPER_SUBMIT_URL + test_id, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((response) => {
            console.log(response);
          });
      }
    } catch (error) {
      console.log("ERROR POSTING TEST SHEET", error);
    }
  };

  return (
    <div id="testresults_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="test" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="contents_title_container">
            <div>시험 결과</div>
            <div id="button_container">
              <button
                className="answer_submit_button"
                onClick={(e) => handleTestContent(e)}
              >
                문제 확인
              </button>
              <button
                className="answer_submit_button"
                onClick={() => handleSubmitClick()}
              >
                답안제출
              </button>
              <input
                id="file_input"
                type="file"
                style={{ display: "none" }}
                onChange={(event) => handleFileChange(event)}
              ></input>
            </div>
          </div>
          <div>
            <div id="test_name">{testName}</div>
            <div>
              <div id="table_label">
                <p className="num">순번</p>
                <p className="student_name">이름</p>
                <p className="score">점수</p>
                <p className="pass_fail">합격/불합격</p>
              </div>
              {studentResults.map((student, i) => (
                <a
                  key={i}
                  className="result_container"
                  href={`/class/${class_id}/test/${test_id}/result/${student.memberId}`}
                >
                  <p className="num">{i + 1}</p>
                  <p className="student_name">{student.name}</p>
                  <p className="score">{student.totalScore}</p>
                  <p className="pass_fail">
                    {student.pass ? "합격" : "불합격"}
                  </p>
                </a>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewTestResult;
