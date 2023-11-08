import gensim
import urllib.request
import nltk
from nltk.tokenize import RegexpTokenizer
from nltk.stem.porter import PorterStemmer

# 구글의 사전 훈련된 Word2Vec 모델을 로드.

word2vec_model = gensim.models.KeyedVectors.load_word2vec_format('C:/Users/SH/Downloads/GoogleNews-vectors-negative300.bin.gz', binary=True)

ps_stemmer = PorterStemmer()
token = RegexpTokenizer('[\w]+')
result2 = [token.tokenize(i) for i in line2]
middle_result2= [r for i in result2 for r in i]
final_result2 = [ps_stemmer.stem(i) for i in middle_result2 if not i in stop_word_eng] # 불용어 제거
print(final_result2)

print(word2vec_model.similarity("advertise","advertising"))