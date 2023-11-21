import "../styles/create_class.css";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import useRefreshToken from "../hooks/useRefreshToken";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const CREATE_URL = "/api/teacher/classes";

const CreateClass = () => {
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  var [name, setName] = useState("");
  var [students, setStudents] = useState();
  const { refresh } = useRefreshToken();

  useEffect(() => {
    var element = document.getElementById("name_err_msg");
    if (name) element.innerText = "";
    else element.innerText = "이름은 필수 입니다.";
  }, [name]);

  const onChangeName = (event) => {
    setName(event.currentTarget.value.trim());
  };

  const onChangeStudents = (event) => {
    setStudents(event.currentTarget.value.trim());
  };

  const submitHandler = async (e) => {
    e.preventDefault();
    const studentList = students.split(" ");
    try {
      await axiosPrivate.post(
        CREATE_URL,
        JSON.stringify({
          name: name,
          members: studentList,
        })
      );
      navigate("/classes");
    } catch (e) {
      console.log("UNEXPECTED ERROR");
      console.log(e);
    }
  };
  return (
    <div id="create_class_page_container">
      <div id="classes_wrapper">
        <div id="contents_container">
          클래스 생성
          <div id="form_container">
            <form onSubmit={submitHandler}>
              <div className="input_container">
                <label>클래스 이름 : </label>
                <input
                  id="name_input"
                  type="text"
                  placeholder="CLASS NAME"
                  onChange={onChangeName}
                ></input>
                <p id="name_err_msg"></p>
              </div>

              <div className="input_container">
                <label>초대할 학생: </label>
                <textarea
                  id="students_input"
                  type="text"
                  onChange={onChangeStudents}
                ></textarea>
              </div>
              <div className="input_container">
                <button id="submit_button"> 완료 </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreateClass;
