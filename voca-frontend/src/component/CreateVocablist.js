import React, { useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

import "../styles/create_vocablist.css";
import { useNavigate, useParams } from "react-router-dom";

const CREATE_VOCABLIST_URL = "/api/vocablist";

const CreateVocablist = () => {
  const axiosPrivate = useAxiosPrivate();
  const { class_id } = useParams();
  const navigate = useNavigate();
  const [file, setFile] = useState();
  const [f, setF] = useState();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [wordList, setWordList] = useState([{ word: "", meaning: "" }]);

  const handleFileUpload = (e) => {
    const f = e.target.files[0];
    setF(f);
    const reader = new FileReader();
    reader.readAsDataURL(f);
    reader.onloadend = () => {
      setFile(reader.result);
    };

    const img = document.getElementById("file_img");
    const no_img = document.getElementById("no_img");

    if (f) {
      img.setAttribute("style", "display: default");
      no_img.setAttribute("style", "display: none");
    } else {
      img.setAttribute("style", "display: none");
      no_img.setAttribute("style", "display: default");
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    var formData = new FormData();
    const json = JSON.stringify({
      name: title,
      description: description,
      contents: wordList,
    });
    formData.append("file", f);

    formData.append("request", new Blob([json], { type: "application/json" }));
    console.log(formData);
    try {
      axiosPrivate
        .post(CREATE_VOCABLIST_URL, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          navigate(`/class/${class_id}/vocablist`);
        });
    } catch (error) {
      console.log("ERROR POSTING VOCABLIST DATA");
    }
  };

  const handleChangeInput = (e, i) => {
    e.preventDefault();
    const { name, value } = e.target;
    const list = [...wordList];
    list[i][name] = value;
    setWordList(list);
  };

  const handleDeleteInput = (e, i) => {
    e.preventDefault();
    const list = [...wordList];
    list.splice(i, 1);
    setWordList(list);
  };

  const handleAddInput = (e) => {
    e.preventDefault();
    const element = document.getElementById("add_input_number");
    const number = parseInt(element.options[element.selectedIndex].value);

    var list = [...wordList];
    for (var i = 0; i < number; i += 1) {
      list = [...list, { word: "", meaning: "" }];
    }
    setWordList(list);
  };

  const handleNameChange = (e) => {
    setTitle(e.target.value);
  };

  const handleDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  return (
    <div id="createvocablist_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} select="vocablist" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="title">단어장 만들기</div>
          <form>
            <div id="info_container">
              <div id="img_container">
                <p style={{ margin: "auto" }} id="no_img">
                  이미지 없음
                </p>
                <img id="file_img" src={file} style={{ display: "none" }}></img>
                <input
                  type="file"
                  accept="image/*"
                  onChange={handleFileUpload}
                  id="upload_img_button"
                ></input>
              </div>
              <div id="info_input_container">
                <div>
                  <label className="input_label">단어장 이름</label>
                  <input
                    type="text"
                    id="title_input"
                    onChange={(e) => handleNameChange(e)}
                  ></input>
                </div>
                <div>
                  <label className="input_label">단어장 설명</label>
                  <input
                    type="text"
                    id="description_input"
                    onChange={(e) => handleDescriptionChange(e)}
                  ></input>
                </div>
              </div>
            </div>
            <div id="control_input_container">
              <select id="add_input_number">
                <option value="1">1개</option>
                <option value="5">5개</option>
                <option value="10">10개</option>
              </select>
              <button id="add_input_button" onClick={(e) => handleAddInput(e)}>
                칸 추가
              </button>
            </div>
            <div id="wordlist_container">
              {wordList.map((word, index) => (
                <div key={index + 1} className="word_container">
                  <label className="word_input_label">{index}</label>
                  <label className="word_input_label">단어</label>
                  <input
                    type="text"
                    className="word_input"
                    name="word"
                    onChange={(e, i) => handleChangeInput(e, index)}
                  ></input>
                  <label className="word_input_label">뜻</label>
                  <input
                    type="text"
                    className="word_input"
                    name="meaning"
                    onChange={(e, i) => handleChangeInput(e, index)}
                  ></input>
                  <button
                    className="delete_input_button"
                    onClick={(e, i) => handleDeleteInput(e, index)}
                  >
                    X
                  </button>
                </div>
              ))}
            </div>
            <div id="submit_button_container">
              <button id="submit_button" onClick={(e) => handleSubmit(e)}>
                단어장 만들기!
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default CreateVocablist;