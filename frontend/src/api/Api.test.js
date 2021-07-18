import {login} from './usersApi'

test("login",async ()=>{
  const a = await login({email:"gineros894@godpeed.com",password:"abcd1234"}).catch(e =>console.log(e))
  console.log(a);
})