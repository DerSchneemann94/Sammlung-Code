from setuptools import setup

setup(
    name="gestureClassifier",
    version="0.1.0",
    description="Module to classify hand gestures",
    author="",
    author_email="",
    license="",
    packages=["src", "src/utils", "src/model"],
    package_data={"src": ["model/*.pth"]},
    include_package_data=True
)
