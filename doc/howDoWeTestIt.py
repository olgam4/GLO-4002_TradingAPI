import os
import re
from fnmatch import fnmatch
from collections import OrderedDict

VALUE_OBJECT = ["StockMarketReturnReport", "HistoryReport", "StockMarketReturn", "InvestmentPosition",
                "Money", "AccountNumber", "TransactionNumber", "DividendPayment", "InvestorId", "StockId", "Quarter", "Dividend"]

INTERFACES = ["AccountRepository", "StockRepository", "MarketRepository",
              "CalculateTotalStrategy", "CalculateFeesStrategy", "ApplicationContext", "ExceptionView", "ValueObject"]

ENTITIES = ["Investor", "Stock", "Reporter",
            "Market", "Account", "Transaction", "Period"]

NOT_TESTED_SPECIAL_CASE = ["BalanceHistory",
                           "Wallet"]

NOT_UNIT_TESTES_SPECIAL_CASE = [
    "ReportMissingTypeException", "ReportType", "ReportUnsupportedTypeException"]


def isNotTested(name, package):
    return ("Assembler" in name) or ("DTO" in name) or ("Type" in name and "Exception" not in name) or (name in VALUE_OBJECT) or (name in INTERFACES) or (name in NOT_TESTED_SPECIAL_CASE) or ("Strategy" in name)


def isUnitTested(name, package):
    return ("Controller" in name) or ("ApplicationService" in name) or (isExceptionUnitTest(name, package)) or (name in ENTITIES)


def isExceptionUnitTest(name, package):
    return ("Exception" in name) and ("Response" not in name) and (package != "views") and (package != "application") and (package != "exceptionhandling") and (name not in NOT_TESTED_SPECIAL_CASE)


def listFiles():
    currentDirectory = os.getcwd()
    parentDirectory = os.path.join(currentDirectory, "../trading-api/src/main")
    pattern = "*.java"
    i = 0
    fileNameList = {}
    for path, subDirs, files in os.walk(parentDirectory):
        for name in files:
            package = path.rsplit("/", 1)[-1]
            if fnmatch(name, pattern):
                i = i + 1
                name = re.sub("\.java$", "", name)
                if(name in VALUE_OBJECT):
                    fileNameList[name] = "Value object/Not mocked"
                elif(isNotTested(name, package)):
                    fileNameList[name] = "Not Mocked"
                elif(isUnitTested(name, package)):
                    fileNameList[name] = "Unit Tests"
                else:
                    fileNameList[name] = "Integrated Tests"
    print(i)
    return fileNameList


def createMarkDownTable(formatedList):
    table = ""
    table = makeTitle(table)
    for key in sorted(formatedList.keys()):
        className = key
        testType = formatedList[key]
        table += "| {} | {} |\n".format(className, testType)
    with open("howDoWeTestIt.md", "w") as outfile:
        outfile.write(table)
    print(table)


def makeTitle(table):
    return "| Class | Test Strategy |\n| :---: | :---: |\n"


if __name__ == "__main__":
    x = listFiles()
    createMarkDownTable(x)
