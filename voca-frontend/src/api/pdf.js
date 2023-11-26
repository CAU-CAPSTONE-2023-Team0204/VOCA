import html2pdf from "html2pdf.js";
import { createElement } from "react";

const TEMPLATE_URL = "/resource/html/test_template.html";

const default_option = {
  margin: 10,
  image: { type: "jpeg", quality: 0.98 },
  html2canvas: { scale: 2 },
  jsPDF: { unit: "pt", format: "a4", orientation: "portrait" },
};

export const generatePDF = (test_title, test_data, user_ids) => {
  //set file name
  const parser = new DOMParser();
  fetch(TEMPLATE_URL)
    .then((response) => response.text())
    .then((html) => {
      const element = document.createElement("div");
      // Use html2pdf to generate PDF from the loaded external content
      const doc = parser.parseFromString(html, "text/html");

      appendProblem(doc, test_data);

      const title_area = doc.getElementsByClassName("test_title").item(0);
      title_area.textContent = test_title;

      const pages = doc.getElementById("pages");
      const page_container = doc
        .getElementsByClassName("page_container")
        .item(0);

      //create page for each user and append pagebreak
      user_ids.forEach((user) => {
        const cloned = page_container.cloneNode(true);
        cloned.childNodes[3].textContent = user;
        pages.appendChild(cloned);
        const page_break = document.createElement("div");
        page_break.setAttribute("class", "page_break");
        pages.appendChild(page_break);
      });

      page_container.remove();

      const strhtml = doc.body.innerHTML;

      html2pdf().from(strhtml).set(default_option).save();
    })
    .catch((error) => {
      console.error("Error fetching the external HTML:", error);
    });
};

const appendProblem = (document, data) => {
  var side = 1;
  var prob_number = 1;
  const left_column = document.getElementsByClassName("left_column").item(0);
  const right_column = document.getElementsByClassName("right_column").item(0);

  if (!data) return;
  data.forEach((word) => {
    const prob_answer = document.createElement("div");
    prob_answer.setAttribute("class", "prob_answer");

    const number = document.createElement("div");
    number.setAttribute("class", "prob_num");
    number.textContent = `${prob_number}`;
    prob_answer.appendChild(number);
    prob_number += 1;

    const prob = document.createElement("div");
    prob.setAttribute("class", "prob");
    prob.textContent = word;
    prob_answer.appendChild(prob);

    const answer_blank = document.createElement("div");
    answer_blank.setAttribute("class", "answer_blank");
    prob_answer.appendChild(answer_blank);

    if (side > 0) left_column.appendChild(prob_answer);
    else right_column.appendChild(prob_answer);
    side *= -1;
  });
};
