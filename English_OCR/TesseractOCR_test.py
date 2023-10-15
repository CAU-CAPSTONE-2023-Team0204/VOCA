import pytesseract
import cv2
import random
import matplotlib.pyplot as plt
from PIL import ImageFont, ImageDraw, Image
import json
import numpy as np


image_path = 'dataset/p4.png'
image = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)  # 흑백 이미지로 읽기

# 이미지 전처리 (예: 이진화)
_, thresholded_image = cv2.threshold(image, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

#custom_config = r'--psm 11'

# 테서렉트로 텍스트 읽기
result = pytesseract.image_to_data(thresholded_image, output_type=pytesseract.Output.DICT,lang='eng+kor')

font_path = "malgun.ttf"  # 한글 폰트 파일 경로
font = ImageFont.truetype(font_path, 40)

converted_result = []
for i in range(len(result['text'])):
    text = result['text'][i].strip()
    if text:  # 빈 텍스트가 아닌 경우에만 처리
        x, y, w, h = result['left'][i], result['top'][i], result['width'][i], result['height'][i]
        confidence = int(result['conf'][i])
        bounding_box = [[x, y], [x + w, y], [x + w, y + h], [x, y + h]]
        converted_result.append({'bounding_box': bounding_box, 'confidence': confidence, 'text': text})

with open('test.json', 'w', encoding='utf-8') as make_file:
    json.dump(converted_result, make_file, ensure_ascii=False, indent="\t")

print("JSON 파일이 성공적으로 생성되었습니다.")

img = Image.open(image_path).convert('RGB')
draw = ImageDraw.Draw(img)

np.random.seed(42)
COLORS = np.random.randint(0, 255, size=(255, 3), dtype="uint8")

for i in converted_result:
    x, y, _, _ = i['bounding_box'][0][0], i['bounding_box'][0][1], i['bounding_box'][2][0] - i['bounding_box'][0][0], i['bounding_box'][2][1] - i['bounding_box'][0][1]
    color_idx = random.randint(0, 255)
    color = tuple([int(c) for c in COLORS[color_idx]])
    draw.rectangle([x, y, x + w, y + h], outline=color, width=2)
    draw.text(((x + x + w) // 2, y - 20), i['text'], font=font, fill=color)

plt.imshow(img)
plt.show()