import { helper } from '@ember/component/helper';

export default helper(function currency(params, hash) {
  const {sign} = hash;
  const [amount] = params;
  return `${amount}${sign}`;
});
