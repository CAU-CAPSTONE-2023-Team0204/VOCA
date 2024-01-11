import React from "react";
import { useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

import "../styles/test_manual.css";

const MANUAL_TEST_URL = "/api/test/manual";

//**********************************************//
//TODO: submit data validation method implement//
//*********************************************//

const TestManualCreate = () => {
  const axiosPrivate = useAxiosPrivate();

  const [test_title, setTitle] = useState("");
  const [problemList, setProblemList] = useState([
    { type: "WORD_TO_MEAN", word: "", meaning: "" },
  ]);

  const handleChange = (e, i) => {
    const { name, value } = e.target;
    const list = [...problemList];
    list[i][name] = value;
    setProblemList(list);
  };

  const handleDeleteInput = (e, i) => {
    e.preventDefault();
    const list = [...problemList];
    list.splice(i, 1);
    setProblemList(list);
  };

  const handleAddInput = (e) => {
    e.preventDefault();
    const element = document.getElementById("problem_type_select");
    const type = element.options[element.selectedIndex].value;
    const list = [...problemList, { type: type, word: "", meaning: "" }];
    setProblemList(list);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(test_title, problemList);
    // try {
    //   const response = await axiosPrivate.post(
    //     MANUAL_TEST_URL,
    //     JSON.stringify({
    //       name: test_title,
    //       problem_list: problemList,
    //     }),
    //     { headers: { "Content-Type": "application/json" } }
    //   );
    // } catch (err) {
    //   console.log("UNEXPECTED_ERROR");
    // }
    //handle errors. then navigate to test view page.
  };

  const handleChangeTitle = (e) => {
    setTitle(e.target.value);
  };

  return (
    <div id="manualtest_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar selected="test" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="contents_title_container">
            <div>수동 출제</div>
            <div id="button_container">
              <button id="submit_button" onClick={(e) => handleSubmit(e)}>
                완료
              </button>
            </div>
          </div>
          <div>
            <form>
              <div id="title_input_container">
                <label>시험 이름</label>
                <input
                  id="title_input"
                  type="text"
                  onChange={(e) => handleChangeTitle(e)}
                />
              </div>
              <select id="problem_type_select">
                <option value="WORD_TO_MEAN"> 뜻 쓰기 </option>
                <option value="MEAN_TO_WORD"> 단어 쓰기</option>
              </select>
              <button onClick={(e) => handleAddInput(e)}>add</button>
              <div id="problem_input_wrapper">
                {problemList.map((problem, i) => (
                  <div key={i} className="problem_input_container">
                    <div className="problem_input_wrapper">
                      <span className="problem_type">
                        {problem.type == "MEAN_TO_WORD"
                          ? "단어 쓰기"
                          : "뜻 쓰기"}
                      </span>
                      <div className="wordmean_input_container">
                        <label>단어</label>
                        <input
                          type="text"
                          name="word"
                          onChange={(e) => handleChange(e, i)}
                        />
                        <label> 뜻 </label>
                        <input
                          type="text"
                          name="meaning"
                          onChange={(e) => handleChange(e, i)}
                        />
                      </div>
                    </div>

                    <button
                      className="delete_input_button"
                      onClick={(e) => handleDeleteInput(e, i)}
                    >
                      X
                    </button>
                  </div>
                ))}
              </div>
            </form>
            <button>완료</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TestManualCreate;
