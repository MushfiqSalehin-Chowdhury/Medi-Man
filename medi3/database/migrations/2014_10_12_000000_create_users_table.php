<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
            $table->string('f_name');
            $table->string('l_name');
            $table->string('image');
            $table->string('phone');
            $table->string('address')->nullable($value = true);
            $table->string('profession')->nullable($value = true);
            $table->string('nid');
            $table->string('w-place')->nullable($value = true);
            $table->string('designation')->nullable($value = true);
            $table->string('birth');
            $table->string('role');
            $table->string('email')->unique();
            $table->string('password');
            $table->rememberToken();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}
