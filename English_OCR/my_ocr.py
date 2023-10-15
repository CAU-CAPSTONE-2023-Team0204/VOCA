import matplotlib.pyplot as plt
import pytesseract
import imutils
import cv2

#url 사용시 필요
#import requests
#import numpy as np

#테스트 출력용 함수
def plt_imshow(title='image', img=None, figsize=(8 ,5)):
    plt.figure(figsize=figsize)
 
    if type(img) == list:
        if type(title) == list:
            titles = title
        else:
            titles = []
 
            for i in range(len(img)):
                titles.append(title)
 
        for i in range(len(img)):
            if len(img[i].shape) <= 2:
                rgbImg = cv2.cvtColor(img[i], cv2.COLOR_GRAY2RGB)
            else:
                rgbImg = cv2.cvtColor(img[i], cv2.COLOR_BGR2RGB)
 
            plt.subplot(1, len(img), i + 1), plt.imshow(rgbImg)
            plt.title(titles[i])
            plt.xticks([]), plt.yticks([])
 
        plt.show()
    else:
        if len(img.shape) < 3:
            rgbImg = cv2.cvtColor(img, cv2.COLOR_GRAY2RGB)
        else:
            rgbImg = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
 
        plt.imshow(rgbImg)
        plt.title(title)
        plt.xticks([]), plt.yticks([])
        plt.show()

# 로컬 이미지 파일의 경로
image_path = 'dataset/box2.png'

# 이미지 파일을 읽어옵니다.
org_image = cv2.imread(image_path,cv2.IMREAD_COLOR) 

# url 이미지 경로
# url = 'https://user-images.githubusercontent.com/69428232/148330274-237d9b23-4a79-4416-8ef1-bb7b2b52edc4.jpg'
 
# image_nparray = np.asarray(bytearray(requests.get(url).content), dtype=np.uint8)
# org_image = cv2.imdecode(image_nparray, cv2.IMREAD_COLOR) 
 
plt_imshow("orignal image", org_image)

image = org_image.copy()
image = imutils.resize(image, width=500)
ratio = org_image.shape[1] / float(image.shape[1])
 
# 이미지를 grayscale로 변환하고 blur를 적용
# 모서리를 찾기위한 이미지 연산
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
blurred = cv2.GaussianBlur(gray, (5, 5,), 0)
edged = cv2.Canny(blurred, 75, 200)
 
plt_imshow(['gray', 'blurred', 'edged'], [gray, blurred, edged])

# contours를 찾아 크기순으로 정렬
cnts = cv2.findContours(edged.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
cnts = imutils.grab_contours(cnts)
cnts = sorted(cnts, key=cv2.contourArea, reverse=True)
 
Cnt = []
 
# 정렬된 contours를 반복문으로 수행하며 4개의 꼭지점을 갖는 도형을 검출
for c in cnts:
	peri = cv2.arcLength(c, True)
	approx = cv2.approxPolyDP(c, 0.02 * peri, True)
 
	if len(approx) == 4:
		Cnt.append(approx)
 
# 만약 추출한 윤곽이 없을 경우 오류
if Cnt is None:
 	raise Exception(("Could not find outline."))

output = image.copy()
cv2.drawContours(output, Cnt, -1, (0, 255, 0), 2)

plt_imshow("Outline", output)

border = 7

for contour in Cnt:
    x, y, w, h = cv2.boundingRect(contour)
    if(w<30 or h < 30):
         continue
    cropped_image = image[y + border:y + h - border, x + border : x + w - border]
    gray1 = cv2.cvtColor(cropped_image, cv2.COLOR_BGR2GRAY)
    blurred1 = cv2.GaussianBlur(gray1, (5, 5,), 0)
    edged1 = cv2.Canny(blurred1, 75, 200)
    detected_text = pytesseract.image_to_string(gray1.copy(), config='--psm 6', lang='eng+kor')
    plt_imshow("Outline", gray1)
    print(detected_text)


# 원본 이미지에 찾은 윤곽을 기준으로 이미지를 보정
#receipt = four_point_transform(org_image, receiptCnt.reshape(4, 2) * ratio)
#plt_imshow("Receipt Transform", receipt)


 
# OCR결과 출력
# print("[INFO] OCR결과:")
# print("==================")
# print(text)
# print("\n")