const withTimeCheck = document.getElementById("with-time");

withTimeCheck.checked = true;

loadAllExams();
loadNewExamTemplateWithoutScroll();

function checkTimeAvailable() {
    if (withTimeCheck.checked === false) {
        document.getElementById("start").disabled = true;
        document.getElementById("end").disabled = true;
        document.getElementById("start").value = "";
        document.getElementById("end").value = "";
    }
    else {
        document.getElementById("start").disabled = false;
        document.getElementById("end").disabled = false;
    }
}

function loadAllExams() {
    var examList = document.querySelector(".exam-list");
    var content = '';
    let exam;
    fetch(`/exams/list`)
        .then(response => response.json())
        .then(data => {
            data.forEach(e => {
                exam = `<div class="exam">
        <div class="exam-content">
            <h3>${e.name}</h3>
            <div class="exam-duration">Thời gian: ${e.timeDuration} phút</div>
            <div class="start-time">Bắt đầu: ${e.startTime.replace(/T/g, ' ')}</div>
            <div class="end-time">Kết thúc: ${e.endTime.replace(/T/g, ' ')}</div>
        </div>
        <div class="exam-button">
            <button class="modify-button" onclick="loadExamDetail(${e.id})">Sửa</button>
            <button class="delete-button" onclick="deleteExam(${e.id})">Xóa</button>
        </div></div>`;
                content += exam;
            })
            examList.innerHTML = content;
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

function loadExamDetail(id) {
    loadExistedExam(id);
    loadAllQuestions(id);
    loadNewQuestionTemplate(id);
}

function loadExistedExam(id) {
    fetch(`/exams/searchById?id=${id}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("input-exam-name").value = data.name;
            document.getElementById("input-exam-description").value = data.description;
            document.getElementById("create-exam-button").value = "Cập nhật";
            document.getElementById("create-exam-button").onclick = function () { updateExistedExam(data.id) }
            document.getElementById("input-time-duration").value = String(data.timeDuration);
            var startDateTime = document.getElementById("start");
            var endDateTime = document.getElementById("end");

            startDateTime.value = data.startTime;
            endDateTime.value = data.endTime;
        })
        .catch(error => {
            console.error("Error:", error);
        });

    document.querySelector(".test-form").scrollIntoView();
}

function updateExistedExam(id) {
    const data = {
        id: id,
        name: document.getElementById("input-exam-name").value,
        description: document.getElementById("input-exam-description").value,
        type: true,
        startTime: document.getElementById("start").value,
        endTime: document.getElementById("end").value,
        timeDuration: parseInt(document.getElementById("input-time-duration").value)
    };

    fetch(`/exams/update`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });
    alert('Cập nhật kỳ thi thành công');
}

function loadNewExamTemplate() {
    document.getElementById("input-exam-name").value = '';
    document.getElementById("input-exam-description").value = '';
    document.getElementById("create-exam-button").value = "Tạo kỳ thi";
    document.getElementById("create-exam-button").onclick = function () { createNewExam() }
    document.getElementById("input-time-duration").value = '';
    document.querySelector(".test-box").innerHTML = '';
    document.querySelector(".new-question-template").innerHTML = '';
    var startDateTime = document.getElementById("start");
    var endDateTime = document.getElementById("end");

    startDateTime.value = '';
    endDateTime.value = '';
    document.querySelector(".test-form").scrollIntoView();
}

function loadNewExamTemplateWithoutScroll() {
    document.getElementById("input-exam-name").value = '';
    document.getElementById("input-exam-description").value = '';
    document.getElementById("create-exam-button").value = "Tạo kỳ thi";
    document.getElementById("create-exam-button").onclick = function () { createNewExam() }
    document.getElementById("input-time-duration").value = '';
    var startDateTime = document.getElementById("start");
    var endDateTime = document.getElementById("end");

    startDateTime.value = '';
    endDateTime.value = '';
}

function createNewExam() {
    const data = {
        name: document.getElementById("input-exam-name").value,
        description: document.getElementById("input-exam-description").value,
        type: true,
        startTime: formatDateTime(document.getElementById("start").value),
        endTime: formatDateTime(document.getElementById("end").value),
        timeDuration: document.getElementById("input-time-duration").value
    };
    fetch(`/exams/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });
    alert('Tạo kỳ thi thành công');
    location.reload();
}

function formatDateTime(datetimeValue) {
    var dateObject = new Date(datetimeValue);

    // Trích xuất các thành phần ngày, tháng, năm, giờ và phút
    var day = dateObject.getDate().toString().padStart(2, '0');
    var month = (dateObject.getMonth() + 1).toString().padStart(2, '0'); // Lưu ý: Tháng trong JavaScript bắt đầu từ 0
    var year = dateObject.getFullYear();
    var hours = dateObject.getHours().toString().padStart(2, '0');
    var minutes = dateObject.getMinutes().toString().padStart(2, '0');
    var seconds = dateObject.getSeconds().toString().padStart(2, '0');

    // Tạo chuỗi mới với định dạng dd-MM-yyyy HH:mm:ss
    var formattedDateTime = `${day}-${month}-${year} ${hours}:${minutes}:${seconds}`;
    return formattedDateTime;
}

function deleteExam(id) {
    fetch(`/exams/delete?id=${id}`, {
        method: 'DELETE'
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });
    alert('Xóa kỳ thi thành công');
    location.reload();
}

function loadAllQuestions(id) {
    var testBox = document.querySelector(".test-box");
    let question;
    testBox.innerHTML = '';
    fetch(`/exams/searchDetailById?id=${id}`)
        .then(response => response.json())
        .then(data => {
            data.question.forEach(q => {
                question = `<div class="question-box">
                <textarea id="question-title-${q.id}" class="question-title" placeholder="Điền câu hỏi">${q.content}</textarea>
                <div class="answer-box">
                    <div class="answer">
                        <input id="answer1-${q.id}" class="answer-label" type="text" placeholder="Điền đáp án 1" value="${q.answer1}">
                        <input id="answer2-${q.id}" class="answer-label" type="text" placeholder="Điền đáp án 2" value="${q.answer2}">
                        <input id="answer3-${q.id}" class="answer-label" type="text" placeholder="Điền đáp án 3" value="${q.answer3}">
                        <input id="answer4-${q.id}" class="answer-label" type="text" placeholder="Điền đáp án 4" value="${q.answer4}">
                        <input id="correct-answer-${q.id}" class="correct-answer" type="text" placeholder="Điền đáp án đúng (1-4)"  value="${q.rightAnswer}">
                        <textarea id="explain-${q.id}" class="explain" placeholder="Giải thích cho đáp án đúng">${q.explain}</textarea>
                    </div>
                </div>
                <div class="question-button">
                    <button onclick="updateQuestion(${q.id}, ${id})">Cập nhật</button>
                    <button onclick="deleteQuestion(${q.id}, ${id})">Xóa</button>
                </div>
            </div>`;
                testBox.innerHTML += question;
            })
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

function loadNewQuestionTemplate(id) {
    document.querySelector(".new-question-template").innerHTML = `<div class="question-box">
    <h2>THÊM MỚI MỘT CÂU HỎI</h2>
    <textarea id="create-question-title" class="question-title" placeholder="Điền câu hỏi"></textarea>
    <div class="answer-box">
        <div class="answer">
            <input id="create-answer1" class="answer-label" type="text" placeholder="Điền đáp án 1">
            <input id="create-answer2" class="answer-label" type="text" placeholder="Điền đáp án 2">
            <input id="create-answer3" class="answer-label" type="text" placeholder="Điền đáp án 3">
            <input id="create-answer4" class="answer-label" type="text" placeholder="Điền đáp án 4">
            <input id="create-correct-answer" class="correct-answer" type="text" placeholder="Điền đáp án đúng (1-4)">
            <textarea id="create-explain" class="explain" placeholder="Giải thích cho đáp án đúng"></textarea>
        </div>
    </div>
    <div class="question-button">
        <button onclick="createNewQuestion(${id})">Thêm câu hỏi</button>
    </div>
</div>`;
}

function createNewQuestion(id) {
    var rightAnswer = document.getElementById("create-correct-answer").value;
    if(rightAnswer > 4 || rightAnswer < 0)
    {
        alert('Đáp án đúng phải từ 1 đến 4');
        return;
    }
    const data = {
        idExam: id,
        content: document.getElementById("create-question-title").value,
        answer1: document.getElementById("create-answer1").value,
        answer2: document.getElementById("create-answer2").value,
        answer3: document.getElementById("create-answer3").value,
        answer4: document.getElementById("create-answer4").value,
        rightAnswer: document.getElementById("create-correct-answer").value,
        explain: document.getElementById("create-explain").value
    };
    fetch(`/questions/create`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });
    alert('Thêm mới câu hỏi thành công!');
    loadExamDetail(id);
}

function updateQuestion(id, idExam) {
    var rightAnswer = document.getElementById(`correct-answer-${id}`).value;
    if(rightAnswer > 4 || rightAnswer < 0)
    {
        alert('Đáp án đúng phải từ 1 đến 4');
        return;
    }
    const data = {
        id: id,
        idExam: idExam,
        content: document.getElementById(`question-title-${id}`).value,
        answer1: document.getElementById(`answer1-${id}`).value,
        answer2: document.getElementById(`answer2-${id}`).value,
        answer3: document.getElementById(`answer3-${id}`).value,
        answer4: document.getElementById(`answer4-${id}`).value,
        rightAnswer: document.getElementById(`correct-answer-${id}`).value,
        explain: document.getElementById(`explain-${id}`).value
    };

    fetch(`/questions/update`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });
    alert('Cập nhật câu hỏi thành công');
    loadAllQuestions(idExam);
}

function deleteQuestion(id, idExam)
{
    fetch(`/questions/delete?id=${id}`, {
        method: 'DELETE'
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });
    alert('Xóa câu hỏi thành công!');
    loadAllQuestions(idExam);
}