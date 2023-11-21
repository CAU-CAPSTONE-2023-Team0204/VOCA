import React, { useEffect, useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import axios from "../api/axios";

import "../styles/register_vocablist.css";

const GET_VOCABLIST_URL = "/api/vocablist";
const REGISTER_VOCABLIST_URL = "/api/vocablist/";

const RegisterVocablist = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [vocabLists, setVocabLists] = useState([]);
  const [selectedVocabList, setSelectedVocabList] = useState(0);

  useEffect(() => {
    const response = axios
      .get(GET_VOCABLIST_URL)
      .then((response) => {
        //setVocabLists(response.vocablists);
        setVocabLists([
          {
            name: "중학교 3학년 추천 단어장",
            image: "/resource/img/vocablist_img_default.png",
            description:
              "중학교 3학년 학생을 위한 추천 단어장입니다. 중학교 3학년 교과서, 교육과정에 맞춰서 제작되었습니다.",
            vocabListContents: [
              { word: "elimination", meaning: "제거" },
              { word: "Korea", meaning: "한국" },
              { word: "legend", meaning: "전설" },
              { word: "myth", meaning: "신화" },
              { word: "crystal", meaning: "수정, 결정" },
              { word: "nocturne", meaning: "야상곡" },
              { word: "emperor", meaning: "황제" },
              { word: "apocalypse", meaning: "멸망" },
              { word: "frost", meaning: "서리" },
            ],
          },
          {
            name: "고등학교 2학년 추천 단어장",
            image: "/resource/img/vocablist_img_default.png",
          },
          {
            name: "토익 추천 단어장",
            image: "/resource/img/vocablist_img_default.png",
          },
          {
            name: "2023 수능 출제 단어장",
            image: "/resource/img/vocablist_img_default.png",
          },
          {
            name: "일상 회화 단어장",
            image: "/resource/img/vocablist_img_default.png",
          },
        ]);
        setIsLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching data", err);
        setIsLoading(false);
      });
  }, []);

  const handleSelectVocablist = (i) => {
    setSelectedVocabList(i);
    console.log(i);
    const element = document.getElementById("modal_window");
    element.style.display = "block";
  };

  const handleCloseModal = () => {
    const element = document.getElementById("modal_window");
    element.style.display = "none";
  };

  const handleRegisterButton = () => {
    axios.post(`/api/vocablist/${vocabLists[selectedVocabList].id}/`);
  };

  return (
    <div id="register_vocablist_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="title">단어장 등록</div>
          <p id="page_description">단어장을 클래스에 등록해보세요!</p>
          <div>
            {isLoading ? (
              <p> Loading ... </p>
            ) : (
              <div id="vocablists_container">
                {vocabLists.map((vocablist, i) => (
                  <div
                    id="vocablist_container"
                    onClick={(e) => handleSelectVocablist(i)}
                  >
                    <img src={vocablist.image} className="vocablist_img"></img>
                    <p className="vocablist_label">{vocablist.name}</p>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>
      <div id="register_modal">
        <div id="modal_window">
          <div id="close_button_container">
            <button id="close_button" onClick={() => handleCloseModal()}>
              X
            </button>
          </div>
          <div id="title_img_container">
            <img
              src={vocabLists[selectedVocabList]?.image}
              id="selected_vocab_img"
            />
            <div id="info_container">
              <p id="selected_title">{vocabLists[selectedVocabList]?.name}</p>
              <p id="selected_description">
                {vocabLists[selectedVocabList]?.description}
              </p>
            </div>
          </div>
          <div id="word_preview_container">
            {vocabLists[selectedVocabList]?.vocabListContents.map((word, i) => (
              <div key={i} id="word_container">
                <p className="word_p">{word.word}</p>
                <p className="word_p">{word.meaning}</p>
              </div>
            ))}
          </div>
          <div id="register_button_container">
            <button
              id="register_button"
              onClick={(e) => handleRegisterButton()}
            >
              클래스에 등록
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterVocablist;
