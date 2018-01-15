<?php

namespace App\Http\Controllers\Auth;

use App\User;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Validator;
use Illuminate\Foundation\Auth\RegistersUsers;
use Illuminate\Support\Facades\Input;

class RegisterController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Register Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles the registration of new users as well as their
    | validation and creation. By default this controller uses a trait to
    | provide this functionality without requiring any additional code.
    |
    */

    use RegistersUsers;

    /**
     * Where to redirect users after registration.
     *
     * @var string
     */
    protected $redirectTo = '/home';

    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest');
    }

    /**
     * Get a validator for an incoming registration request.
     *
     * @param  array  $data
     * @return \Illuminate\Contracts\Validation\Validator
     */
    protected function validator(array $data)
    {
        return Validator::make($data, [
            'name' => 'required|string|max:255',
            'f_name' => 'max:255',
            'l_name' => 'max:255',
            'image' => 'max:255',
            'phone' => 'max:255',
            'address' => 'max:255',
            'profession' => 'max:255',
            'nid' => 'max:255',
            'w-place' => 'max:255',
            'designation' => 'max:255',
            'birth' => 'max:255',
            'role' => 'max:255',
            'email' => 'required|string|email|max:255|unique:users',
            'password' => 'required|string|min:6|confirmed',
        ]);
    }

    /**
     * Create a new user instance after a valid registration.
     *
     * @param  array  $data
     * @return \App\User
     */
    protected function create(array $data)
    {

        if( Input::hasFile('image') ){
            $file = Input::file('image');
            $file->move( 'uploads', $file->getClientOriginalName() );
            $data['image'] = $file->getClientOriginalName();

        }else{
            $data['image'] = '';
        }


        return User::create([
            'name' => $data['name'],
            'f_name' => $data['f_name'],
            'l_name' => $data['l_name'],
            'image' =>  $data['image'],
            'phone' => $data['phone'],
            'address' => $data['address'],
            'profession' => $data['profession'],
            'nid' => $data['nid'],
            'w-place' => $data['w-place'],
            'designation' => $data['designation'],
            'birth' => $data['birth'],
            'role' => $data['role'],
            'email' => $data['email'],
            'password' => bcrypt($data['password']),
        ]);
    }

    // public function store()
    // {
    //     // dd(request()->all());
    //     RegisterClientController::create([
    //         'name' => $request('name'),
    //         'f-name' => $request('f-name'),
    //         'l-name' => $request('l-name'),
    //         'phone' => $request('phone'),
    //         'address' => $request('address'),
    //         'profession' => $request('profession'),
    //         'nid' => $request('nid'),
    //         'w-place' => $request('w-place'),
    //         'designation' => $request('designation'),
    //         'birth' => $request('birth'),
    //         'role' => $request('role'),
    //         'email' => $request('email'),
    //         'password' => request('password'),

    //     ]);
        

    // }
}
