<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\Input;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return view('home');
    }
    public function imageUpload(Request $request)
    {

			echo 'Uploaded';
			$file = Input::file('file');
			$file->move('uploads', $file->getClientOriginalName());
			echo '';
      // $storagePath = Storage::putFile('public',$request->image);
      //  $imagename = $request->image->getClientOriginalName();
      //  $imageurl = Storage::url($imagename);
      //  $item = Item::find($itemid);
      //  $item->image = $storagePath;
      //  $item->save();
      //  return json_encode($imageurl);
    }
}
