import 'package:cognitive_app/pages/layout.dart';
import 'package:cognitive_app/services/auth_service.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:logger/logger.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final Logger logger = Logger();
  bool isLogin = true; // Toggle for login or signup
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  final AuthService authService = AuthService();

  Future<void> _authenticate() async {
    try {
      if (isLogin) {
        logger.i('Logging in...');
        logger.i('Username: ${emailController.text.trim()}');
        logger.i('Password: ${passwordController.text.trim()}');
        final response = await authService.login(
          emailController.text.trim(),
          passwordController.text.trim(),
        );
        if (response['result'] == 'success') {
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(builder: (context) => const LayoutPage()),
          );
          logger.w('Login Successful: $response');
        }
      } else {
        logger.i('Signing up...');
        final response = await authService.registerBySpring(
          emailController.text.trim(),
          usernameController.text.trim(),
          passwordController.text.trim(),
        );
        logger.i('Sign Up Successful: $response');
        if (response['result'] == 'success') {
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(builder: (context) => const LayoutPage()),
          );
          logger.w('Login Successful: $response');
        }
      }
    } catch (e) {
      logger.i('Error: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(e.toString())),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[100],
      body: Center(
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                'COGNITIVE FINDER',
                style: TextStyle(
                  fontFamily: 'Cursive',
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              const Text(
                'Safety in Every Step',
                style: TextStyle(
                  fontSize: 14,
                  color: Colors.grey,
                ),
              ),
              const SizedBox(height: 30),
              Container(
                margin: const EdgeInsets.symmetric(horizontal: 20),
                padding: const EdgeInsets.all(20),
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(12),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey.withOpacity(0.2),
                      blurRadius: 8,
                      spreadRadius: 2,
                      offset: const Offset(0, 4),
                    )
                  ],
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    // Tabs: Login and Sign Up
                    Row(
                      children: [
                        Expanded(
                          child: GestureDetector(
                            onTap: () => setState(() => isLogin = true),
                            child: Column(
                              children: [
                                Text(
                                  'Login',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                    color: isLogin ? Colors.black : Colors.grey,
                                  ),
                                ),
                                if (isLogin)
                                  Container(
                                    margin: const EdgeInsets.only(top: 4),
                                    height: 2,
                                    width: 40,
                                    color: Colors.black,
                                  ),
                              ],
                            ),
                          ),
                        ),
                        const SizedBox(width: 30),
                        Expanded(
                          child: GestureDetector(
                            onTap: () => setState(() => isLogin = false),
                            child: Column(
                              children: [
                                Text(
                                  'Sign Up',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                    color: isLogin ? Colors.grey : Colors.black,
                                  ),
                                ),
                                if (!isLogin)
                                  Container(
                                    margin: const EdgeInsets.only(top: 4),
                                    height: 2,
                                    width: 40,
                                    color: Colors.black,
                                  ),
                              ],
                            ),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 20),

                    // Input Fields
                    if (!isLogin) ...[
                      const Text('Username',
                          style: TextStyle(fontWeight: FontWeight.bold)),
                      const SizedBox(height: 8),
                      TextField(
                        controller: usernameController,
                        decoration: InputDecoration(
                          hintText: 'Enter your username',
                          filled: true,
                          fillColor: Colors.grey[100],
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(8),
                            borderSide: BorderSide.none,
                          ),
                        ),
                      ),
                      const SizedBox(height: 16),
                    ],
                    const Text('Email',
                        style: TextStyle(fontWeight: FontWeight.bold)),
                    const SizedBox(height: 8),
                    TextField(
                      controller: emailController,
                      decoration: InputDecoration(
                        hintText: 'Enter your email',
                        filled: true,
                        fillColor: Colors.grey[100],
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(8),
                          borderSide: BorderSide.none,
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),
                    const Text('Password',
                        style: TextStyle(fontWeight: FontWeight.bold)),
                    const SizedBox(height: 8),
                    TextField(
                      controller: passwordController,
                      obscureText: true,
                      decoration: InputDecoration(
                        hintText: 'Enter your password',
                        filled: true,
                        fillColor: Colors.grey[100],
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(8),
                          borderSide: BorderSide.none,
                        ),
                      ),
                    ),
                    const SizedBox(height: 20),

                    // Login/Sign Up Button
                    SizedBox(
                      width: double.infinity,
                      height: 48,
                      child: ElevatedButton(
                        onPressed: _authenticate,
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Colors.black,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                        child: Text(
                          isLogin ? 'Login' : 'Sign Up',
                          style: const TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                    const SizedBox(height: 20),
                    // Google & Apple Buttons
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        _socialButton(
                            'Google',
                            const Icon(FontAwesomeIcons.google,
                                color: Colors.black)),
                        const SizedBox(width: 20),
                        _socialButton(
                            'Apple',
                            const Icon(FontAwesomeIcons.apple,
                                color: Colors.black)),
                      ],
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  // Social Button Widget
  Widget _socialButton(String text, Icon icon) {
    return ElevatedButton.icon(
      onPressed: () {},
      icon: icon,
      label: Text(
        text,
        style: const TextStyle(color: Colors.black),
      ),
      style: ElevatedButton.styleFrom(
        backgroundColor: Colors.white,
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
        side: const BorderSide(color: Colors.grey),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(8),
        ),
      ),
    );
  }
}
