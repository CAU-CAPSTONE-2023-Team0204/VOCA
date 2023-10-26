import json

def read_input():
    try:
        with open('c:/Users/SH/Desktop/CAU-CAPSTONE/VOCA/English_OCR/req_test.json',encoding='UTF8') as file:
            datas = json.load(file)

        testInfo = datas['testInfo']
        problemNo = testInfo['problemNo']
        answer = testInfo['Answer']
        if(problemNo != len(answer)):
            raise json.JSONDecodeError('문제 개수와 답의 개수가 맞지 않습니다.')
        

        #print(answer)
        return answer

    except FileNotFoundError:
        print("File not found")

    except json.JSONDecodeError:
        print("올바른 JSON 형식이 아닙니다.")

answer = read_input()
