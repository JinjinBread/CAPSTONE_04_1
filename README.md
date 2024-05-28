![slice](https://capsule-render.vercel.app/api?type=slice&height=300&color=104085&text=Back-End&reversal=false&textBg=false&fontColor=ffffff&fontSize=70&fontAlignY=39&fontAlign=78&animation=blink)
![Group 159](https://github.com/jiyou228/hansung_frontend_00/assets/143888533/79d08b7d-93e9-483a-be9f-25e5c014da03)

# [Job학다식 사이트로 이동](https://jobhakdasik.site)

<br>

# Languages and Platforms
## [Back-End](https://github.com/JinjinBread/CAPSTONE_04_1)
<img src="https://img.shields.io/badge/java-%23007396.svg?&style=for-the-badge&logo=java&logoColor=white" />
<img src="https://img.shields.io/badge/spring-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white" /> <br>

<img src="https://img.shields.io/badge/mysql-%234479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white" />
<img src="https://img.shields.io/badge/redis-%23DC382D.svg?&style=for-the-badge&logo=redis&logoColor=white" />
<img src="https://img.shields.io/badge/amazon%20aws-%23232F3E.svg?&style=for-the-badge&logo=amazon%20aws&logoColor=white"/>

## [Front-End](https://github.com/jiyou228/hansung_frontend_00)
<img src="https://img.shields.io/badge/react-%2361DAFB.svg?&style=for-the-badge&logo=react&logoColor=black" />
<img src="https://img.shields.io/badge/javascript-%23F7DF1E.svg?&style=for-the-badge&logo=javascript&logoColor=black"/>
<img src="https://img.shields.io/badge/openai-%23412991.svg?&style=for-the-badge&logo=openai&logoColor=white" />


## Deep Learning(GAN)
<img src="https://img.shields.io/badge/python-%233776AB.svg?&style=for-the-badge&logo=python&logoColor=white" />
<img src="https://img.shields.io/badge/flask-%23000000.svg?&style=for-the-badge&logo=flask&logoColor=white" />

<br>

# Social Login
<img src ="https://oauth.net/images/oauth-2-sm.png"/>

### OAuth2를 사용한 소셜 로그인
<img src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlC_kMLlL1cuwqSiYidzQkcko84d8WX-UQuw&s"/>

![social](https://github.com/jiyou228/hansung_frontend_00/assets/143888533/ffd533a6-65cb-4992-8880-07d65d0b25b6)




<br>

# IDLE
<img src="https://img.shields.io/badge/intellij%20idea-%23000000.svg?&style=for-the-badge&logo=intellij%20idea&logoColor=white" />
<img src="https://img.shields.io/badge/visual%20studio%20code-%23007ACC.svg?&style=for-the-badge&logo=visual%20studio%20code&logoColor=white" />

<br>

# System
![아키텍처0](https://github.com/jiyou228/hansung_frontend_00/assets/143888533/6d19255c-9471-4817-8967-e9df66134827)

<br>

# Function
<label for="name" style="color: #0099ff; font-size: 22px; text-align: center; display: block;"> **Chat GPT를 이용한 자기소개서 생성 및 첨삭** </label><br>

![자기소개서](https://github.com/jiyou228/hansung_frontend_00/assets/143888533/db65cf90-8b55-466d-b986-32f5856402fd)


<label for="name" style="color: #0099ff; font-size: 22px; text-align: center; display: block;"> **Star GAN을 이용한 사진 합성** </label><br>

![gan](https://github.com/jiyou228/hansung_frontend_00/assets/143888533/59aa35c0-33a7-4ff9-b420-de4cbc2eab87)

![minji](https://github.com/jiyou228/hansung_frontend_00/assets/143888533/26c0bb03-a357-4325-9a99-ed24482fd0bf)


## Dependencies
* [Python 3.5+](https://www.continuum.io/downloads)
* [PyTorch 0.4.0+](http://pytorch.org/)
* [TensorFlow 1.3+](https://www.tensorflow.org/) (optional for tensorboard)


## Downloading datasets
To download the CelebA dataset:
```bash
git clone https://github.com/yunjey/StarGAN.git
cd StarGAN/
bash download.sh celeba
```

To download the RaFD dataset, you must request access to the dataset from [the Radboud Faces Database website](http://www.socsci.ru.nl:8180/RaFD2/RaFD?p=main). Then, you need to create a folder structure as described [here](https://github.com/yunjey/StarGAN/blob/master/jpg/RaFD.md).

## Training networks
To train StarGAN on CelebA, run the training script below. See [here](https://github.com/yunjey/StarGAN/blob/master/jpg/CelebA.md) for a list of selectable attributes in the CelebA dataset. If you change the `selected_attrs` argument, you should also change the `c_dim` argument accordingly.

```bash
# Train StarGAN using the CelebA dataset
python main.py --mode train --dataset CelebA --image_size 128 --c_dim 5 \
               --sample_dir stargan_celeba/samples --log_dir stargan_celeba/logs \
               --model_save_dir stargan_celeba/models --result_dir stargan_celeba/results \
               --selected_attrs Black_Hair Blond_Hair Brown_Hair Male Young

# Test StarGAN using the CelebA dataset
python main.py --mode test --dataset CelebA --image_size 128 --c_dim 5 \
               --sample_dir stargan_celeba/samples --log_dir stargan_celeba/logs \
               --model_save_dir stargan_celeba/models --result_dir stargan_celeba/results \
               --selected_attrs Black_Hair Blond_Hair Brown_Hair Male Young
```

To train StarGAN on RaFD:

```bash
# Train StarGAN using the RaFD dataset
python main.py --mode train --dataset RaFD --image_size 128 \
               --c_dim 8 --rafd_image_dir data/RaFD/train \
               --sample_dir stargan_rafd/samples --log_dir stargan_rafd/logs \
               --model_save_dir stargan_rafd/models --result_dir stargan_rafd/results

# Test StarGAN using the RaFD dataset
python main.py --mode test --dataset RaFD --image_size 128 \
               --c_dim 8 --rafd_image_dir data/RaFD/test \
               --sample_dir stargan_rafd/samples --log_dir stargan_rafd/logs \
               --model_save_dir stargan_rafd/models --result_dir stargan_rafd/results
```

To train StarGAN on both CelebA and RafD:

```bash
# Train StarGAN using both CelebA and RaFD datasets
python main.py --mode=train --dataset Both --image_size 256 --c_dim 5 --c2_dim 8 \
               --sample_dir stargan_both/samples --log_dir stargan_both/logs \
               --model_save_dir stargan_both/models --result_dir stargan_both/results

# Test StarGAN using both CelebA and RaFD datasets
python main.py --mode test --dataset Both --image_size 256 --c_dim 5 --c2_dim 8 \
               --sample_dir stargan_both/samples --log_dir stargan_both/logs \
               --model_save_dir stargan_both/models --result_dir stargan_both/results
```

To train StarGAN on your own dataset, create a folder structure in the same format as [RaFD](https://github.com/yunjey/StarGAN/blob/master/jpg/RaFD.md) and run the command:

```bash
# Train StarGAN on custom datasets
python main.py --mode train --dataset RaFD --rafd_crop_size CROP_SIZE --image_size IMG_SIZE \
               --c_dim LABEL_DIM --rafd_image_dir TRAIN_IMG_DIR \
               --sample_dir stargan_custom/samples --log_dir stargan_custom/logs \
               --model_save_dir stargan_custom/models --result_dir stargan_custom/results

# Test StarGAN on custom datasets
python main.py --mode test --dataset RaFD --rafd_crop_size CROP_SIZE --image_size IMG_SIZE \
               --c_dim LABEL_DIM --rafd_image_dir TEST_IMG_DIR \
               --sample_dir stargan_custom/samples --log_dir stargan_custom/logs \
               --model_save_dir stargan_custom/models --result_dir stargan_custom/results
```


## Using pre-trained networks
To download a pre-trained model checkpoint, run the script below. The pre-trained model checkpoint will be downloaded and saved into `./stargan_celeba_128/models` directory.

```bash
$ bash download.sh pretrained-celeba-128x128
```

To translate images using the pre-trained model, run the evaluation script below. The translated images will be saved into `./stargan_celeba_128/results` directory.

```bash
$ python main.py --mode test --dataset CelebA --image_size 128 --c_dim 5 \
                 --selected_attrs Black_Hair Blond_Hair Brown_Hair Male Young \
                 --model_save_dir='stargan_celeba_128/models' \
                 --result_dir='stargan_celeba_128/results'
```

## Citation
If you find this work useful for your research, please cite our [paper](https://arxiv.org/abs/1711.09020):
```
@inproceedings{choi2018stargan,
author={Yunjey Choi and Minje Choi and Munyoung Kim and Jung-Woo Ha and Sunghun Kim and Jaegul Choo},
title={StarGAN: Unified Generative Adversarial Networks for Multi-Domain Image-to-Image Translation},
booktitle={Proceedings of the IEEE Conference on Computer Vision and Pattern Recognition},
year={2018}
}
```
