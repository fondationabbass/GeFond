export const ngbToDate = (date?:any): Date => {
    return new Date(date.year, date.month - 1, date.day);
}
export const dateToNgb = (date?: Date): any => {
   return  {
        year: date.getFullYear(),
        month: date.getMonth() + 1,
        day: date.getDate()
    };
}