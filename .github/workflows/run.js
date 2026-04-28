import { restoreCache } from "@actions/cache";

const res = await restoreCache('foo', 'scala-steward', ['scala-steward']);
console.log(res);
