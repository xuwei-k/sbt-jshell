import { restoreCache } from "@actions/cache";

console.log(cache);
const res = await restoreCache('foo', 'scala-steward', ['scala-steward']);
console.log(res);
