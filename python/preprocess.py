from os import listdir
from os.path import isfile,join
import os
import pip
#import pandas as pd

def import_or_install(package):
    try:
        __import__(package)
    except ImportError:
        pip.main(['install', package]) 

def main():
    import_or_install("pandas")
    import pandas as pd
    print("\n#############################Preprocessing#############################\n")
    print("Importing data set...")
    file="C:\\Users\\SIDDHARTHA\\workspace_sid\\Visualization\\nightingale-data.xlsx"
    xl = pd.ExcelFile(file)
    print(xl.sheet_names)

if __name__ == '__main__':
    main()