# 네이버 Papago NMT API 예제
import requests
import json
 
# 1. APP 등록 - access token
CLIENT_ID, CLIENT_SECRET = 'V5G4BJykAUoHjWCucuNt', 'lXHGvMLd6f'
 

def translate(text):
    # 2. request (en 외 언어로 번역도 가능)
    #text = '시험을 치다'
    url = 'https://openapi.naver.com/v1/papago/n2mt'
    headers = {
        'Content-Type': 'application/json',
        'X-Naver-Client-Id': CLIENT_ID,
        'X-Naver-Client-Secret': CLIENT_SECRET
    }
    data = {'source': 'ko', 'target': 'en', 'text': text}
    
    # post 방식으로 서버 쪽으로 요청
    response = requests.post(url, json.dumps(data), headers=headers) 
    # print(response)  # 정상 응답인지 확인
    
    # 3. response(en) -> en_txt
    # print(response.text)  # 응답 출력 - 내용이 dictionary인 str
    
    # json() 후 key 값을 사용하여 원하는 텍스트 접근
    en_text = response.json()['message']['result']['translatedText']
    print(en_text)
    return en_text