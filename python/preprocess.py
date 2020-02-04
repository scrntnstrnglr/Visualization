from os import listdir
from os.path import isfile,join
import os
import pandas as pd
    
def main():
    print("\n#############################Preprocessing#############################\n")
    print("Importing data set...")
    file="C:\\Users\\SIDDHARTHA\\workspace_sid\\Visualization\\nightingale-data.xlsx"
    xl = pd.ExcelFile(file)
    print(xl.sheet_names)

if __name__ == '__main__':
    main()