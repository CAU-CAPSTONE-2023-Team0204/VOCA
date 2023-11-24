import React, { useState, getParam, useEffect } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";

import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useParams } from "react-router-dom";

//page that represents each students' test result.
import "../styles/view_test_results.css";

const TEST_RESULT_URL = "/api/test/result/";
const PAPER_SUBMIT_URL = "/api/test/start/";

const ViewTestResult = () => {
  const { class_id } = useParams();
  const [file, setFile] = useState();
  const { key } = useParams();
  const axiosPrivate = useAxiosPrivate();

  // try {
  //   const response = axiosPrivate.get(TEST_RESULT_URL + { key });
  // } catch (err) {
  //   console.log(err);
  // }

  const response = {
    name: "정기 테스트 1",
    studentresults: [
      { name: "문서형", score: 10, pass: false },
      { name: "장세환", score: 11, pass: true },
      { name: "차선우", score: 12, pass: true },
    ],
  };

  const handleSubmitClick = () => {
    const element = document.getElementById("file_input");
    element.click();
  };

  const handleFileChange = async (event) => {
    await setFile(event.target.files[0]);
    console.log(file);
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      const response = await axiosPrivate.post(
        PAPER_SUBMIT_URL + `${key}})}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log(response);
    } else {
      alert("NO FILE SELECTED");
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
            <div id="test_name">{response.name}</div>
            <div>
              <div id="table_label">
                <p className="num">번호</p>
                <p className="student_name">이름</p>
                <p className="score">점수</p>
                <p className="pass_fail">합격/불합격</p>
              </div>
              {response.studentresults.map((student, i) => (
                <a key={i} className="result_container" href="">
                  <p className="num">{i}</p>
                  <p className="student_name">{student.name}</p>
                  <p className="score">{student.score}</p>
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
