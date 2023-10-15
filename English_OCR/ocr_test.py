import easyocr
import numpy as np
import cv2
import random
import matplotlib.pyplot as plt
from PIL import ImageFont, ImageDraw, Image
import json

image2 = 'dataset/p3.png'
image = cv2.imread(image2, cv2.IMREAD_GRAYSCALE)  # 흑백 이미지로 읽기

# 이미지 전처리 (예: 이진화)
_, thresholded_image = cv2.threshold(image, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

try:
    reader = easyocr.Reader(['ko', 'en'])
    result = reader.readtext(image)
    
    # 넘파이 배열을 파이썬 리스트로 변환하면서 데이터 타입 변환
    converted_result = []
    for item in result:
        bounding_box = item[0]
        bounding_box = [[int(point[0]), int(point[1])] for point in bounding_box]  # 좌표값을 정수로 변환한 리스트로 변환
        
        # confidence 값이 int32 타입이면 정수로 변환, 그 외의 경우 그대로 둠
        text = int(item[1]) if isinstance(item[1], np.int32) else item[1]
        
        confidence = item[2]
        converted_result.append({'bounding_box': bounding_box, 'confidence': confidence, 'text': text})

    with open('test.json', 'w', encoding='utf-8') as make_file:
        json.dump(converted_result, make_file, ensure_ascii=False, indent="\t")

    print("JSON 파일이 성공적으로 생성되었습니다.")

except Exception as e:
    print("에러 발생:", e)

img    = cv2.imread('dataset/p3.png')

img = Image.fromarray(img)
font = ImageFont.truetype("malgun.ttf", 40)
draw = ImageDraw.Draw(img)

np.random.seed(42)
COLORS = np.random.randint(0, 255, size=(255, 3),dtype="uint8")


for i in result :
    x = i[0][0][0]
    y = i[0][0][1]
    w = i[0][1][0] - i[0][0][0]
    h = i[0][2][1] - i[0][1][1]
    
    color_idx = random.randint(0,255)
    color = [int(c) for c in COLORS[color_idx]]
    
#    cv2.putText(img, str(i[1]), (int((x + x + w) / 2) , y-2), cv2.FONT_HERSHEY_SIMPLEX, 1, color, 2)
#    img = cv2.rectangle(img, (x, y), (x+w, y+h), color, 2)
    draw.rectangle(((x, y), (x+w, y+h)), outline=tuple(color), width=2)
    draw.text((int((x + x + w) / 2) , y-2),str(i[1]), font=font, fill=tuple(color),)

plt.imshow(img)
plt.show()
# cv2.imshow("test",img)
# cv2.waitKey(0)