import {AllowFileType, Parser} from '../types/enums';

export const fileTypeParseTypeAdapter = (type: AllowFileType): Parser => {
    switch (type){
        case AllowFileType.csv:
            return Parser.csv

        case AllowFileType.json:
            return Parser.json

        case AllowFileType.xml:
            return Parser.xml

        case AllowFileType.yaml:
            return Parser.yaml
    }
};
