// js小数运算出现多位小数如何解决

function getDecimalDigitalNumber(num){
   return num.toString().split(".")[1].length;
}

// 精准加法
function accAdd(arg1, arg2) {
   let r1 = getDecimalDigitalNumber(arg1);
   let r2 = getDecimalDigitalNumber(arg2);
   let m = Math.pow(10, Math.max(r1, r2));
   return (arg1 * m + arg2 * m) / m;
}

// 精准乘法
function accMul(arg1, arg2) {
   let m = 0;
   m += getDecimalDigitalNumber(arg1);
   m += getDecimalDigitalNumber(arg2);
   let s1 = Number(arg1.toString().replace(".", ""));
   let s2 = Number(arg2.toString().replace(".", ""));
   return s1 * s2 / Math.pow(10, m);
}

// 精准除法
function accDiv(arg1, arg2) {
   let t1 = getDecimalDigitalNumber(arg1);
   let t2 = getDecimalDigitalNumber(arg2);
   with (Math) {
       let r1 = Number(arg1.toString().replace(".", ""));
       let r2 = Number(arg2.toString().replace(".", ""));
       return (r1 / r2) * pow(10, t2 - t1);
   }
}

Number.prototype.add = function (arg) {
   return accAdd(arg, this);
};

Number.prototype.mul = function (arg) {
   return accMul(arg, this);
};

Number.prototype.div = function (arg) {
   return accDiv(this, arg);
};
