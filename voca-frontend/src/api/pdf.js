import html2pdf from "html2pdf.js";

const TEMPLATE_URL = "../../resource/html/test_template.html";

const default_option = {
  margin: 10,
  image: { type: "jpeg", quality: 0.98 },
  html2canvas: { scale: 2 },
  jsPDF: { unit: "pt", format: "a4", orientation: "portrait" },
};

export const generatePDF = (test_title, test_data) => {
  const random_word = "distortion";
  //set file name
  fetch(TEMPLATE_URL)
    .then((response) => response.text())
    .then((html) => {
      const element = document.createElement("div");
      // Use html2pdf to generate PDF from the loaded external content
      const doc = parser.parseFromString(html, "text/html");

      element.innerHTML = html;

      appendProblem(doc, test_data);
      const title_area = doc.getElementById("test_title");
      title_area.textContent = test_title;

      const strhtml = doc.body.innerHTML;

      html2pdf().from(strhtml).set(default_option).save();
    })
    .catch((error) => {
      console.error("Error fetching the external HTML:", error);
    });
  const parser = new DOMParser();
};

const appendProblem = (document, data) => {
  var side = 1;
  var prob_number = 1;
  const left_column = document.getElementById("left_column");
  const right_column = document.getElementById("right_column");

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
